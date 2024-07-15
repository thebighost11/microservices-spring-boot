package com.example.msclub.club;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "club")
public class Club {

    private String id;
    private String clubName;
    private String clubLeague;

    @Id
    private String id;
    private String clubPlace;
    private String clubDate;

}

