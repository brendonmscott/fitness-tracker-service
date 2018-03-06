package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.LoginResponseDto;
import com.bscott.fitness.tracker.dto.RegisterUserDto;
import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.dto.UserLoginDto;
import com.bscott.fitness.tracker.exception.AuthenticationException;
import com.bscott.fitness.tracker.exception.RegistrationException;
import com.bscott.fitness.tracker.model.Account;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.service.UserService;
import com.bscott.fitness.tracker.translator.AccountTranslator;
import com.bscott.fitness.tracker.translator.UserTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

@Api(value = "/users")
@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;
    @Autowired
    private UserTranslator userTranslator;
    @Autowired
    private AccountTranslator accountTranslator;

    @ApiOperation(value = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was retrieved successfully")
    })
    @GetMapping("/{id}")
    public ResponseEntity findUserById(
            @ApiParam(value = "The id of the User to find", required = true) @PathVariable("id") String id){

        User user = userService.findUserById(id);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User was registered successfully")
    })
    @PostMapping("/register")
    public ResponseEntity registerUser(@ApiParam(value = "The user to register", required = true) @Valid RegisterUserDto registerUserDto){

        Account newAccount;
        try {
            newAccount = userService.registerUser(
                    userTranslator.createNewUser(registerUserDto),
                    userTranslator.getLoginCredentials(registerUserDto));

        } catch (RegistrationException e) {
            logger.error("An error occurred trying to register a new user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(accountTranslator.toDto(newAccount));
    }

    @ApiOperation(value = "User Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was logged in successfully")
    })
    @PostMapping("/login")
    public ResponseEntity login(@ApiParam(value = "The user to login", required = true) @Valid UserLoginDto login) {

        try {
            // Authenticate the user using the credentials provided
            User user = authenticate(login.getEmail(), login.getPassword());

            // Issue a token for the user
            LoginResponseDto loginResponseDto = issueToken(user);

            // Return the token on the response
            return ResponseEntity.ok(loginResponseDto);

        } catch (AuthenticationException e) {
            logger.error("An error occurred during login", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ApiOperation(value = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was updated successfully")
    })
    @PutMapping("/")
    public  ResponseEntity updateUser(@ApiParam(value = "The user to update", required = true) @Valid UserDto userDto){

        User updatedUser = userService.updateUser(userTranslator.toEntity(userDto));

        return ResponseEntity.ok(userTranslator.toDto(updatedUser));
    }

    @ApiOperation(value = "Delete a user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User was deleted successfully")
    })
    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@ApiParam(value = "The userId to delete", required = true) @PathVariable("id") String id){

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private User authenticate(String username, String password) throws AuthenticationException {

        User user = userService.login(username, password);

        // Throw an Exception if user not found
        if (user == null) {
            throw new AuthenticationException("User not found");
        } else {
            return user;
        }
    }

    private LoginResponseDto issueToken(User user) {
        // Issue a token
        // The issued token must be associated to a user
        // Return the issued token
        // FIXME
//        return new LoginResponseDto(Jwts.builder()
//                .setSubject(user.getId())
//                .claim("roles", user.getRoles())
//                .setIssuedAt(new Date())
//                .signWith(SignatureAlgorithm.HS256, System.getProperty(Constants.TOKEN_KEY_PROPERTY)).compact());
        return null;
    }
}
