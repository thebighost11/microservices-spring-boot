package com.example.msplayer.player;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@Slf4j
@RequiredArgsConstructor
@RefreshScope
public class PlayerController {
    private final IPlayerService playerService;

    @GetMapping
    public List<PlayerDTO> getAllPlayers() {
        return playerService.findAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerDTO getPlayerById(@PathVariable("id") Long id) {return playerService.getPlayerById(id);}

    @PostMapping
    public PlayerDTO createPlayer(@RequestBody PlayerDTO playerDTO) {return playerService.savePlayer(playerDTO);}

    @PutMapping("/{id}")
    public PlayerDTO updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {return playerService.savePlayer(playerDTO);}

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayerById(id);
        }

}
