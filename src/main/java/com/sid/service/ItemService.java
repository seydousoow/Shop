package com.sid.service;

import com.sid.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    Page<Item> getItems(String category, Pageable pageable);

    Page<Item> getItemsByBrand(String category, List<String> brands, Pageable pageable);

    List<String> getAllBrands(String category);

    Item getItem(String code);

    Item add(Item item);

    Item update(Item item);

    void delete(String id);
}
