package com.example.msclub.club;

import lombok.Builder;

@Builder
public record ClubDTO(String clubId, String clubPlace, String clubDate) {

    // Static method to map from Stock entity to StockDTO
    public static ClubDTO mapFromEntity(Club club) {
        return ClubMapper.INSTANCE.toDto(club);
    }

    // Static method to map from StockDTO to Stock entity
    public static Club mapToEntity(ClubDTO clubDTO) {
        return ClubMapper.INSTANCE.toEntity(clubDTO);
    }

}
