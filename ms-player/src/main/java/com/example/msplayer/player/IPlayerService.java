package com.example.msplayer.player;



import java.util.List;

public interface IPlayerService {
    PlayerDTO getPlayerById(Long playerId);
    PlayerDTO savePlayer(PlayerDTO playerDTO);
    List<PlayerDTO> findAllPlayers();
    void deletePlayerById(Long playerId);
    void consumeClub(String message);
}

