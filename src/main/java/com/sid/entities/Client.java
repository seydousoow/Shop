package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sid.config.auditing.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends AuditMetadata {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String idClient;
    @Indexed(unique = true)
    private String code = RandomStringUtils.randomAlphanumeric(20);
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String country;
    @Email
    @Indexed(sparse = true, unique = true)
    private String email;
    @Indexed(sparse = true, unique = true) // sparse true makes the field to ignore to unique index if the field is set to null
    private String telephone;
}
