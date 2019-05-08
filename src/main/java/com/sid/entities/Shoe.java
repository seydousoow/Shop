package com.sid.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
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
    private LocalDateTime addedAt;
    private String category;
    private String code;
    private String name;
    private String brand;
    private String model;
    private double boughtPrice;
    private double sellingPrice;
    private String imageUrl;
    private String description;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Collection<Size> listSize = new ArrayList<>();
    
    public Shoe() {
    	this.addedAt = LocalDateTime.now();
    }
}
