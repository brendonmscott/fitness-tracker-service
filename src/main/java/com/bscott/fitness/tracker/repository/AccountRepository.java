package com.bscott.fitness.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.bscott.fitness.tracker.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByEmail(String email);

    default Boolean accountExists(String email){

        Account account = findByEmail(email);
        return account != null;
    }
}
