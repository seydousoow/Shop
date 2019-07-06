package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Document("users")
@Data
@JsonPropertyOrder({"firstName", "lastName", "username", "password", "activated", "email", "telephone"})
public class AppUser implements Serializable {

    @Id
    private String userId;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    @Length(min = 4, max = 25, message = "The username must contains at least 4 characters without exceeding 25 characters")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Boolean activated = true;

    @JsonProperty(required = true)
    @Email(message = "This format of this email is not valid")
    @NotBlank(message = "The email cannot be empty")
    @Indexed(unique = true)
    private String email;

    @JsonProperty(required = true)
    @NotNull(message = "The phone number must be filled")
    @Indexed(unique = true)
    private String telephone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean isAdmin = false;

    @DBRef
    private Collection<AppRole> roles = new ArrayList<>();
}
