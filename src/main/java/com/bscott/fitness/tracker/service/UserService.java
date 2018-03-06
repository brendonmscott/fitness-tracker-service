package com.bscott.fitness.tracker.service;

import com.bscott.fitness.tracker.exception.AuthenticationException;
import com.bscott.fitness.tracker.exception.RegistrationException;
import com.bscott.fitness.tracker.model.Account;
import com.bscott.fitness.tracker.model.LoginCredentials;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.repository.AccountRepository;
import com.bscott.fitness.tracker.repository.LoginRepository;
import com.bscott.fitness.tracker.repository.UserRepository;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private LoginRepository loginDao;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository, LoginRepository loginRepository){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.loginDao = loginRepository;
    }

    public User findUserById(@NotEmpty String id){
        return userRepository.findOne(id);
    }

    public List<User> findUsersByName(@NotEmpty String firstName, @NotEmpty String lastName){
        return userRepository.findUsersByFirstNameAndLastName(firstName, lastName);
    }

    public User login(@NotEmpty String userName, @NotEmpty String password) throws AuthenticationException {

        LoginCredentials credentials = loginDao.findLoginCredentialsByUserNameAndPassword(userName, password);

        String userId;

        if (credentials != null) {
            userId = credentials.getUserId();
            return userRepository.findOne(userId);
        } else {
            throw new AuthenticationException("User not found");
        }
    }

    public Account registerUser(@Valid @NotNull User user, @Valid @NotNull LoginCredentials loginCredentials) throws RegistrationException{

        // Make sure there is not an existing userName address
        if(accountRepository.accountExists(loginCredentials.getUserName())){
            logger.warn("Registration attempted for existing userName address {}", loginCredentials.getUserName());
            throw new RegistrationException("There is already an account with this userName");
        }

        User newUser = userRepository.save(user);

        Account newAccount = new Account();
        newAccount.setOwner(newUser);
        accountRepository.save(newAccount);

        loginCredentials.setUserId(newAccount.getOwner().getId());
        loginDao.save(loginCredentials);

        return newAccount;
    }

    public User updateUser(@Valid @NotNull User user){
        userRepository.save(user);

        return user;
    }

    public void deleteUser(@NotEmpty String id){
        userRepository.delete(id);
    }
}
