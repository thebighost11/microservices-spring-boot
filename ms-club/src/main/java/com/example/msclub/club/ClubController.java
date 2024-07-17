package com.example.msclub.club;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@Slf4j
@RequiredArgsConstructor
@RefreshScope
public class ClubController {
    private final IClubService clubService;

    @GetMapping
    public List<ClubDTO> getAllClubs() {
        return clubService.findAllClubs();
    }

    @GetMapping("/{id}")
    public ClubDTO getClubById(@PathVariable String id) {
        log.info("! club is delivered");
        return clubService.getClubById(id);
    }

    @PostMapping
    public ClubDTO createClub(@RequestBody ClubDTO clubDTO) {
        return clubService.saveClub(clubDTO);
    }

    @PutMapping("/{id}")
    public ClubDTO updateClub(@PathVariable String id, @RequestBody ClubDTO clubDTO) {
        return clubService.saveClub(clubDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteClub(@PathVariable String id) {
        clubService.deleteClubById(id);
        clubService.sendClub(id);
    }

}
