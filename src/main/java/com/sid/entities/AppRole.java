package com.sid.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sid.config.auditing.AuditMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@EqualsAndHashCode(callSuper = true)
@Document("roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppRole extends AuditMetadata implements Serializable {

    @Id
    @JsonProperty(access = WRITE_ONLY)
    private String roleId;
    @Indexed(unique = true)
    private String roleName;

}
