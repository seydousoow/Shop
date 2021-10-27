package com.sid.service;

import com.sid.dto.ItemFiltersDto;
import com.sid.dto.ItemSizeDto;
import com.sid.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ItemService {

    Page<Item> getItems(String category, List<String> brands, List<String> sizes, Pageable pageable);

    List<String> getAllBrands(String category);

    Item getItemByCode(String code);

    Item add(Item item);

    Item update(Item item);

    Collection<ItemSizeDto> addSizesToItem(String itemId, ItemSizeDto size);

    Collection<ItemSizeDto> updateSizes(String itemId, Collection<ItemSizeDto> sizes);

    Collection<ItemSizeDto> deleteSize(String itemId, String size);

    void delete(String id);

    ItemFiltersDto getItemsFilters(String category);
}
