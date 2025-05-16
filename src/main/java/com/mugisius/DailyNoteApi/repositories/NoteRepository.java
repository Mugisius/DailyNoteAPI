package com.mugisius.DailyNoteApi.repositories;

import com.mugisius.DailyNoteApi.data.Note;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository
        extends CrudRepository<Note, Long> {

    @Modifying
    @Query("INSERT INTO note VALUES (default, :title, :author, :description, :location, :temperature)")
    void addTask(String title, String author, String description, String location, Double temperature);

    List<Note> findNotesByAuthor(String author);

    Note findNoteById(long id);

    @Modifying
    @Query("UPDATE note SET title = :title, description = :description WHERE id = :id")
    void updateNote(long id, String title, String description);
}
