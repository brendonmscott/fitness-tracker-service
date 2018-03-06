package com.bscott.fitness.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginResponseDto {

    private String token;

    @JsonCreator
    public LoginResponseDto(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
