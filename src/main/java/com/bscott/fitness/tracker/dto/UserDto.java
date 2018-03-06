package com.bscott.fitness.tracker.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private String id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private String birthDate;
    @NotEmpty
    private List<String> roles;
    private List<String> favoriteFoodItems;
    private List<String> customizedFoodItems;

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getRoles() {

        if(roles == null){
            roles = new ArrayList<>();
        }

        return roles;
    }

    public void setFavoriteFoodItems(List<String> favoriteFoodItems) {
        this.favoriteFoodItems = favoriteFoodItems;
    }

    public List<String> getFavoriteFoodItems() {

        if(favoriteFoodItems == null){
            favoriteFoodItems = new ArrayList<>();
        }

        return favoriteFoodItems;
    }

    public List<String> getCustomizedFoodItems() {

        if(customizedFoodItems == null){
            customizedFoodItems = new ArrayList<>();
        }

        return customizedFoodItems;
    }

    public void setCustomizedFoodItems(List<String> customizedFoodItems) {
        this.customizedFoodItems = customizedFoodItems;
    }
}
