package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = Constants.LOGIN_COLLECTION)
public class LoginCredentials {

    @Id
    private String id;
    private String userId;
    @NotEmpty
    @Email
    private String userName;
    @NotEmpty
    private String password;
}
