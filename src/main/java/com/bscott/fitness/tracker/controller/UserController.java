package com.bscott.fitness.tracker.controller;

import com.bscott.fitness.tracker.dto.UserDto;
import com.bscott.fitness.tracker.model.User;
import com.bscott.fitness.tracker.service.UserService;
import com.bscott.fitness.tracker.translator.UserTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private UserService userService;
    @Autowired
    private UserTranslator userTranslator;

    @ApiOperation(value = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was retrieved successfully")
    })
    @GetMapping("/{id}")
    public ResponseEntity findUserById(
            @ApiParam(value = "The id of the User to find", required = true) @PathVariable("id") String id) {

        User user = userService.findUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userTranslator.toDto(user));
    }

    @ApiOperation(value = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was updated successfully")
    })
    @PutMapping
    public  ResponseEntity updateUser(@ApiParam(value = "The user to update", required = true) @Valid UserDto userDto) {

        User updatedUser = userService.updateUser(userTranslator.toEntity(userDto));

        return ResponseEntity.ok(userTranslator.toDto(updatedUser));
    }

    @ApiOperation(value = "Delete a user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "User was deleted successfully")
    })
    @DeleteMapping("/{id}")
    ResponseEntity deleteUser(@ApiParam(value = "The userId to delete", required = true) @PathVariable("id") String id) {

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
