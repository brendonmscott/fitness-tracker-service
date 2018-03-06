package com.bscott.fitness.tracker.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserLoginDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
