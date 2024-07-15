package com.example.msplayer.player;

import lombok.Builder;

@Builder
public record PlayerDTO(Long playerId, Float price, String clubId, ClubDTO clubDTO) {


}
