package com.sid.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CartItem {
    private String code;
    private String category;
    private String size;
    private String quantity;
    private String price;
}
