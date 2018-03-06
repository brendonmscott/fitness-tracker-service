package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLoginCredentialsUserName(String userName);
    List<User> findUsersByFirstNameAndLastName(String firstName, String lastName);
}
