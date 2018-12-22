package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findUsersByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findById(String id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
