package com.example.msplayer.player;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerServiceImpl implements IPlayerService {
    private final PlayerRepository playerRepository;
    private final ClubClient clubClient;
    private final PlayerMapper playerMapper;
    private final RestTemplate restTemplate;
    private static final String CLUB_SERVICE_URL = "http://MS-CLUB/clubs/";

    @Override
    public PlayerDTO getPlayerById(Long id) {
       /* Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);*/

        return playerRepository.findById(id).map(player -> {
            ResponseEntity<ClubDTO> response = restTemplate.exchange(
                    CLUB_SERVICE_URL + player.getClubId(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    ClubDTO.class
            );
            ClubDTO clubDTO = response.getBody();
            if (clubDTO == null) {
                throw new IllegalArgumentException("Club not found");
            }
            PlayerDTO playerDTO = playerMapper.toDto(player);
            return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
        }).orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }

    @Override
    public PlayerDTO savePlayer(PlayerDTO playerDTO) {
        ClubDTO clubDTO = clubClient.getClubById(playerDTO.clubId());
        if (clubDTO != null) {
            Player player = playerMapper.toEntity(playerDTO);
            playerRepository.save(player);
            return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
        } else {
            throw new IllegalArgumentException("Club not found");
        }
    }

    @Override
    public List<PlayerDTO> findAllPlayers() {
        /*Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);*/

        return playerRepository.findAll().stream()
                .map(player -> {
                    ResponseEntity<ClubDTO> response = restTemplate.exchange(
                            CLUB_SERVICE_URL + player.getClubId(),
                            HttpMethod.GET,
                            HttpEntity.EMPTY,
                            ClubDTO.class
                    );
                    ClubDTO clubDTO = response.getBody();
                    if (clubDTO == null) {
                        throw new IllegalArgumentException("Club not found");
                    }
                    PlayerDTO playerDTO = playerMapper.toDto(player);
                    return new PlayerDTO(playerDTO.playerId(), playerDTO.price(), playerDTO.clubId(), clubDTO);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deletePlayerById(Long id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Player not found");
        }
    }

    @KafkaListener(topics = "club-topic", groupId = "group_id")
    public void consumeClub(String idClub) {
        playerRepository.findAllByClubId(idClub).forEach(
                player -> deletePlayerById(player.getId())
        );
        log.info("Consumed club: {}", idClub);
    }
}
