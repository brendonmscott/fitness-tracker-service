package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.repository.UserRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(@NotEmpty String id) {
        return userRepository.findOne(id);
    }

    public List<User> findUsersByName(@NotEmpty String firstName, @NotEmpty String lastName) {
        return userRepository.findUsersByFirstNameAndLastName(firstName, lastName);
    }

    public User updateUser(@Valid @NotNull User user) {
        userRepository.save(user);

        return user;
    }

    public void deleteUser(@NotEmpty String id) {
        userRepository.delete(id);
    }
}
