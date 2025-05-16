package com.mugisius.DailyNoteApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugisius.DailyNoteApi.data.Note;
import com.mugisius.DailyNoteApi.repositories.NoteRepository;
import com.mugisius.DailyNoteApi.proxies.WeatherProxy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(path = "/api")
public class NoteController {

    private final NoteRepository noteRepository;
    private final WeatherProxy weatherProxy;

    public NoteController(NoteRepository noteRepository,
                          WeatherProxy weatherProxy) {
        this.noteRepository = noteRepository;
        this.weatherProxy = weatherProxy;
    }

    @PostMapping
    public void addNote(
            @RequestBody Note note
    ) throws JsonMappingException, JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        noteRepository.addTask(
                note.getTitle(),
                username,
                note.getDescription(),
                note.getLocation(),
                getTemperature(note.getLocation())
        );
    }

    @GetMapping("/all")
    public Iterable<Note> findAllNotes() {
        return noteRepository.findAll();
    }

    @GetMapping
    public Iterable<Note> findAllNotesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        return noteRepository.findNotesByAuthor(user);
    }

    @PatchMapping
    public void patchNote(
            @RequestBody Note note,
            HttpServletRequest request
    ) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        String author = noteRepository.findNoteById(note.getId()).getAuthor();
        if (user.equals(author))
            noteRepository.updateNote(
                    note.getId(),
                    note.getTitle(),
                    note.getDescription()
            );
        else
            throw new AccessDeniedException("Not allowed");
    }

    private double getTemperature(String location)
            throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String response = weatherProxy.getCoordinates(location,1, "33cbfaecec2b43f80caf0d9f10885f50");
        JsonNode root = mapper.readTree(response);
        double lat = root.iterator().next().get("lat").asDouble();
        double lon = root.iterator().next().get("lon").asDouble();

        response = weatherProxy.getWeather(lat, lon, "33cbfaecec2b43f80caf0d9f10885f50");
        root = mapper.readTree(response);
        double temp = root.get("main").get("temp").asDouble();

        return temp - 273.15;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleException(AccessDeniedException e) { return e.getMessage(); }
}
