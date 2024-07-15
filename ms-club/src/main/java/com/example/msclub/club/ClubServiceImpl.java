package com.example.msclub.club;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ClubServiceImpl implements IClubService {
    private ClubRepository repository;
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "club-topic";

    public List<ClubDTO> findAllClubs() {
        return repository.findAll().stream()
                .map(ClubDTO::mapFromEntity)
                .collect(Collectors.toList());
    }

    public ClubDTO getClubById(String clubId) {
        Club club = repository.findById(clubId).orElse(null);
        return ClubDTO.mapFromEntity(club);
    }

    public ClubDTO saveClub(ClubDTO clubDTO) {
        Club club = ClubDTO.mapToEntity(clubDTO);
        club = repository.save(club);
        return ClubDTO.mapFromEntity(club);
    }

    public void deleteClubById(String clubId) {
        repository.deleteById(clubId);
    }

    public void sendClub(String clubId) {
        kafkaTemplate.send(TOPIC, clubId);
        log.info("Produced club: {}", clubId);
    }
}

