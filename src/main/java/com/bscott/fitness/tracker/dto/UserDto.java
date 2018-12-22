package com.bscott.fitness.tracker.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private String id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private String birthDate;
    @NotEmpty
    private Set<String> roles = new HashSet<>();
    private List<String> favoriteFoodItems = new ArrayList<>();
    private List<String> customizedFoodItems = new ArrayList<>();
}
