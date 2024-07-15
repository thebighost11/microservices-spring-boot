package com.example.msplayer.player;

import lombok.Builder;

@Builder
public record ClubDTO(String clubId, String clubPlace, String clubDate) {
}
