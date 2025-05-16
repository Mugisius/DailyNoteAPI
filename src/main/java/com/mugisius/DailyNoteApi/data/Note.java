package com.mugisius.DailyNoteApi.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Note {

    @Id
    private long id;

    private String title;

    private String author;
    private String description;
    private String location;
    private double temperature;

}
