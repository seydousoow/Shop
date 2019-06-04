package com.sid.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("images")
@Data @NoArgsConstructor @AllArgsConstructor
@JsonPropertyOrder("base64Image")
public class ItemsImages {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String _id;
    @Indexed(unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;
    private String base64Image;
}
