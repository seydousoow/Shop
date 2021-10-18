package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sid.config.auditing.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Document("users")
@Data
@JsonPropertyOrder({"firstname", "lastname", "username"})
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends AuditMetadata implements Serializable {
    @Id
    private String userId;
    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    @Length(min = 4, max = 25, message = "The username must contains at least 4 characters without exceeding 25 characters")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean isActive = true;

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
    private boolean isAdmin = false;

    @DBRef
    private Collection<AppRole> roles;

    public AppUser(AppUser user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.isActive = user.isActive();
        this.roles = user.getRoles();
        this.telephone = user.getTelephone();
        this.email = user.getEmail();
        this.isAdmin = user.isAdmin();
    }

}
