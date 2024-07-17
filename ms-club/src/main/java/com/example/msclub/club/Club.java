package com.example.msclub.club;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "club")
public class Club {

    @Id
    private String id;
    private String clubName;
    private String clubLeague;
}

