package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Document("roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRole implements Serializable {

    @Id
    @JsonProperty(access = WRITE_ONLY)
    private String roleId;
    private String roleName;

}
