package com.sid.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Shoes")
@Data
public class Shoe {
    @Id
    private String idShoe;
    @Indexed( direction = IndexDirection.DESCENDING)
    private LocalDateTime addedAt = LocalDateTime.now(ZoneId.of("UTC"));
    private String category;

    @Indexed(unique = true)
    private String code;
    private String brand;
    private String model;
    private double buyingPrice;
    private double sellingPrice;
    @Transient
    private String image;
    private String description;

    private Collection<Size> listSize = new ArrayList<>();
}
