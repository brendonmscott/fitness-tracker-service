package com.bscott.fitness.tracker.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AccountDto {

    private String id;
    private String email;
    @NotNull
    @Valid
    private UserDto owner;
    private List<UserDto> friends;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<UserDto> getFriends() {

        if(friends == null){
            friends = new ArrayList<>();
        }

        return friends;
    }

    public void setFriends(List<UserDto> friends) {
        this.friends = friends;
    }
}
