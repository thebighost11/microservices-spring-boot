package com.example.msplayer.player;

import lombok.Builder;

@Builder
public record ClubDTO(String clubId, String clubName, String clubLeague) {
}
