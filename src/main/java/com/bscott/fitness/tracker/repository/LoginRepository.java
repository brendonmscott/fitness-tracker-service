package com.bscott.fitness.tracker.repository;

import com.bscott.fitness.tracker.model.LoginCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<LoginCredentials, String> {

    LoginCredentials findLoginCredentialsByUserName(String userName);
    LoginCredentials findLoginCredentialsByUserNameAndPassword(String userName, String password);
}
