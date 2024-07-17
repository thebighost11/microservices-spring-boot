package com.example.msclub.club;

import lombok.Builder;

@Builder
public record ClubDTO(String clubId, String clubName, String clubLeague) {

    public static ClubDTO mapFromEntity(Club club) {
        return ClubMapper.INSTANCE.toDto(club);
    }

    public static Club mapToEntity(ClubDTO clubDTO) {
        return ClubMapper.INSTANCE.toEntity(clubDTO);
    }

}
