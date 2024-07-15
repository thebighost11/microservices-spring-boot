package com.example.msplayer.player;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements IPlayerService {
    private PlayerRepository playerRepository;
    private ClubClient clubClient;
    private PlayerMapper playerMapper;
    private RestTemplate restTemplate;
    private static final String EVENT_SERVICE_URL = "http://MS-EVENT/clubs/";

    public PlayerDTO getPlayerById(Long id) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return playerRepository.findById(id).map(player -> {
            ResponseEntity<ClubDTO> response = restTemplate.exchange(
                    EVENT_SERVICE_URL + player.getClubId(),
                    HttpMethod.GET,
                    entity,
                    ClubDTO.class
            );
            ClubDTO clubDTO = response.getBody();
            PlayerDTO playerDTO = playerMapper.toDto(player);
            return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
        }).orElseThrow(() -> new IllegalArgumentException("Player not found"));
        }

    public PlayerDTO savePlayer(PlayerDTO playerDTO) {
        ClubDTO clubDTO = clubClient.getClubById(playerDTO.clubId());
        if(clubDTO != null) {
            Player player = playerMapper.toEntity(playerDTO);
            playerRepository.save(player);
            return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
        }
        else throw new IllegalArgumentException("Club not found");
    }

    public List<PlayerDTO> findAllPlayers() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return playerRepository.findAll().stream()
                .map(player -> {
                    ResponseEntity<ClubDTO> response = restTemplate.exchange(
                            EVENT_SERVICE_URL + player.getClubId(),
                            HttpMethod.GET,
                            entity,
                            ClubDTO.class
                    );
                    ClubDTO clubDTO = response.getBody();
                    PlayerDTO playerDTO = playerMapper.toDto(player);
                    return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
                })
                .collect(Collectors.toList());
    }

    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }

    @KafkaListener(topics = "club-topic", groupId = "group_id")
    public void consumeClub(String idClub) {
       playerRepository.findAllByClubId(idClub).forEach(
               player -> deletePlayerById(player.getId())
       );
        log.info("Consumed club: {}", idClub);
    }

}

