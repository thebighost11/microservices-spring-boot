package com.example.msclub.club;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ClubServiceImpl implements IClubService {
    private KafkaTemplate<String, String> stringKafkaTemplate;


    private ClubRepository repository;

    private static final String TOPIC = "club-topic";

    public List<ClubDTO> findAllClubs() {
        return repository.findAll().stream().map(ClubDTO::mapFromEntity).collect(Collectors.toList());
    }
    public void sendClub(String clubId) {
        stringKafkaTemplate.send(TOPIC, clubId);
        log.info("club: {}", clubId);
    }

    public ClubDTO saveClub(ClubDTO clubDTO) {
        Club club = ClubDTO.mapToEntity(clubDTO);
        club = repository.save(club);
        return ClubDTO.mapFromEntity(club);
    }

    public ClubDTO getClubById(String clubId) {
        Club club = repository.findById(clubId).orElse(null);
        return ClubDTO.mapFromEntity(club);
    }


    public void deleteClubById(String clubId) {
        repository.deleteById(clubId);
    }


}

