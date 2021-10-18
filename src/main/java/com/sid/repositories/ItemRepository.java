package com.sid.repositories;

import com.sid.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    Item findItemByCodeEquals(String code);

    Page<Item> findItemsByCategoryEquals(String category, Pageable pageable);

    Page<Item> findItemsByCategoryEqualsAndBrandIsIn(String category, List<String> brand, Pageable pageable);

    List<Item> findByCategoryEquals(String category);
}
