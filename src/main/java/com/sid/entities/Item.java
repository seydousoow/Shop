package com.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    private String idItem;
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime addedAt = LocalDateTime.now(ZoneId.of("UTC"));
    @Indexed(unique = true)
    private String code = RandomStringUtils.randomAlphanumeric(20);

    private String category;
    private String brand;
    private String name;
    private double buyingPrice;
    private double sellingPrice;
    private String description;
    @Transient
    private String image;
    private int quantity;

    private List<ItemSize> sizes = new ArrayList<>();
}
