package com.example.msplayer.player;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-EVENT", configuration = FeignConfig.class)
public interface ClubClient {

    @GetMapping("/clubs/{id}")
    @CircuitBreaker(name="msClub",fallbackMethod ="fallbackGetClubById")
    ClubDTO getClubById(@PathVariable("id") String id);

    default ClubDTO fallbackGetClubById(String id, Throwable throwable) {
        return new ClubDTO("0", "Fallback club", "0");
    }

}
