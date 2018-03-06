package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document(collection = Constants.USER_COLLECTION)
public class User {

    @Id
    private String id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    private List<String> roles;
    private LoginCredentials loginCredentials;
    private List<ObjectId> favoriteFoodItems;
    private List<ObjectId> customizedFoodItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getRoles() {

        if(roles == null){
            roles = new ArrayList<>();
        }

        return roles;
    }

    public LoginCredentials getLoginCredentials() {
        return loginCredentials;
    }

    public void setLoginCredentials(LoginCredentials loginCredentials) {
        this.loginCredentials = loginCredentials;
    }

    public void setFavoriteFoodItems(List<ObjectId> favoriteFoodItems) {
        this.favoriteFoodItems = favoriteFoodItems;
    }

    public List<ObjectId> getFavoriteFoodItems() {

        if(favoriteFoodItems == null){
            favoriteFoodItems = new ArrayList<>();
        }

        return favoriteFoodItems;
    }

    public List<ObjectId> getCustomizedFoodItems() {

        if(customizedFoodItems == null){
            customizedFoodItems = new ArrayList<>();
        }

        return customizedFoodItems;
    }

    public void setCustomizedFoodItems(List<ObjectId> customizedFoodItems) {
        this.customizedFoodItems = customizedFoodItems;
    }
}
