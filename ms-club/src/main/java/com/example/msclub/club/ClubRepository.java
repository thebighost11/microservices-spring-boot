package com.example.msclub.club;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ClubRepository extends MongoRepository<Club, String> {
}
