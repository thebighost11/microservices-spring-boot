package com.example.msclub.club;


import java.util.List;

public interface IClubService {
    List<ClubDTO> findAllClubs();
    ClubDTO getClubById(String clubId);
    ClubDTO saveClub(ClubDTO clubDTO);
    void deleteClubById(String clubId);
    void sendClub(String clubId);
}

