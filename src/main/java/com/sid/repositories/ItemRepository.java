package com.sid.repositories;

import com.sid.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Item findItemByCodeEquals(@Param("code") String code);
    Page<Item> findItemsByCategoryEquals(@Param("category") String category, Pageable pageable);
    Page<Item> findItemsByCategoryEqualsAndBrandIsIn(@Param("category") String category, @Param("brand") List<String> brand, Pageable pageable);
}
