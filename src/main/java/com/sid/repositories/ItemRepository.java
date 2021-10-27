package com.sid.repositories;

import com.sid.entities.Item;
import com.sid.projection.ItemFiltersProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

    Optional<Item> findItemByCodeEquals(String code);

    @Query(value = "{category: ?0}", collation = "{'locale': 'fr', 'strength' : 1}")
    Page<Item> findItemsByCategoryEquals(String category, Pageable pageable);

    @Query(value = "{'category' : ?0}", fields = "{'sizes': 1, 'brand': 1}", collation = "{'locale': 'fr', 'strength' : 1}")
    Collection<ItemFiltersProjection> findItemsFiltersByCategory(String category);

    @Query(value = "{category: ?0, brand: {$in: ?1}}", collation = "{'locale': 'fr', 'strength' : 1}")
    Page<Item> findItemsByCategoryEqualsAndBrandIsIn(String category, List<String> brand, Pageable pageable);

    @Query(value = "{category: ?0}", collation = "{'locale': 'fr', 'strength' : 1}")
    List<Item> findByCategory(String category);

    @Query(value = "{category: ?0, sizes: {$elemMatch: {size: {$in: ?1}, quantity: {$gt: 0}}}}", collation = "{'locale': 'fr', 'strength' : 1}")
    Page<Item> findItemsByCategoryAndSizesIn(String category, List<String> sizes, Pageable pageable);

    @Query(value = "{category: ?0, brand: {$in: ?1}, sizes: {$elemMatch: {size: {$in: ?2}, quantity: {$gt: 0}}}}", collation = "{'locale': 'fr', 'strength' : 1}")
    Page<Item> findByCategoryAndBrandInAndSizesIn(String category, List<String> brands, List<String> sizes, Pageable pageable);
}
