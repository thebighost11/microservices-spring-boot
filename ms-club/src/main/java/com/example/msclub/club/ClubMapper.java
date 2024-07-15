package com.example.msclub.club;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClubMapper {
    ClubMapper INSTANCE = Mappers.getMapper(ClubMapper.class);
    @Mapping(target = "clubId", source = "id")
    ClubDTO toDto(Club stock);
    @Mapping(target = "id", source = "clubId")
    Club toEntity(ClubDTO clubDTO);
}
