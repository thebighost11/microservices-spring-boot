package com.example.msplayer.player;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);
    @Mapping(source = "id", target = "playerId")
    PlayerDTO toDto(Player player);
    @Mapping(source = "playerId", target = "id")
    Player toEntity(PlayerDTO playerDTO);

}
