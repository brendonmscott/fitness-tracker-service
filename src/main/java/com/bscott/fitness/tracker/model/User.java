package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = Constants.USER_COLLECTION)
public class User {

    @Id
    private String id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotNull
    private LocalDate birthDate;
    private Set<String> roles = new HashSet<>();
    private List<ObjectId> favoriteFoodItems = new ArrayList<>();
    private List<ObjectId> customizedFoodItems = new ArrayList<>();

    public User(String firstName, String lastName, LocalDate birthDate, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
