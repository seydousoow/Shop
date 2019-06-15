package com.sid.service;

import com.sid.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Services {
    Page<Item> getItems(String category, Pageable pageable);
    Page<Item> getItemsByBrand(String category, List<String> brands, Pageable pageable);
    Item getItem(String id);
    Item add(Item item);
    Item update(Item item);
    void delete(String id);
}
