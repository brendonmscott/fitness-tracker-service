package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = Constants.ACCOUNT_COLLECTION)
public class Account {

    @Id
    private String id;
    private String email;
    private User owner;
    private List<User> friends;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getFriends() {

        if(friends == null){
            friends = new ArrayList<>();
        }

        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
