package com.sid.entities;

import com.sid.config.auditing.AuditMetadata;
import com.sid.dto.ItemSizeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item extends AuditMetadata {

    @Id
    private String idItem;
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime addedAt = LocalDateTime.now(ZoneId.of("UTC"));
    @Indexed(unique = true)
    private String code = RandomStringUtils.randomAlphanumeric(20);

    private String category;
    private String brand;
    private String model;
    private String name;
    private double buyingPrice;
    private double sellingPrice;
    private String description;

    private String picture;
    private int quantity;

    private Collection<ItemSizeDto> sizes = new ArrayList<>();
}
