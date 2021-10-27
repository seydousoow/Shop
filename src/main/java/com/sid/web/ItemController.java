package com.sid.web;

import com.sid.dto.ItemFiltersDto;
import com.sid.dto.ItemSizeDto;
import com.sid.entities.Item;
import com.sid.exception.RestException;
import com.sid.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/{category}")
    public ResponseEntity<Page<Item>> getItems(@PathVariable String category,
                                               @RequestParam(required = false) List<String> brands,
                                               @RequestParam(required = false) List<String> sizes,
                                               @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "24") int size,
                                               @RequestParam(required = false, defaultValue = "addedAt") String sort,
                                               @RequestParam(required = false, defaultValue = "DESC") String direction) {
        if (isBlank(category)) throw new RestException("Please select the category of the products you want to view.");

        var cat = category.toLowerCase();
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction.toUpperCase()), sort));
        return ResponseEntity.ok(itemService.getItems(cat, brands, sizes, pageable));
    }

    @GetMapping(value = "/{category}/filters")
    public ResponseEntity<ItemFiltersDto> getItemsFilters(@PathVariable String category) {
        if (isBlank(category)) throw new RestException("Please select the category of the products you want to view.");
        return ResponseEntity.ok(itemService.getItemsFilters(category.toLowerCase()));
    }

    @GetMapping("/details")
    public Item getItem(@RequestParam String code) {
        return itemService.getItemByCode(code);
    }

    @GetMapping(value = "/brands/{category}")
    public List<String> getAllBrands(@PathVariable String category) {
        if (isBlank(category)) throw new RestException("Please select the category of the brands you want to view.");
        return itemService.getAllBrands(category.toLowerCase());
    }

    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.add(item);
    }

    @PutMapping
    public Item updateItem(@RequestBody Item item) {
        return itemService.update(item);
    }

    @PostMapping("/{id}/sizes")
    public ResponseEntity<Collection<ItemSizeDto>> addSizeToItem(@PathVariable("id") String itemId, @RequestBody ItemSizeDto size) {
        return ResponseEntity.ok(itemService.addSizesToItem(itemId, size));
    }

    @PutMapping("/{id}/sizes")
    public ResponseEntity<Collection<ItemSizeDto>> updateItemSizes(@PathVariable("id") String itemId, @RequestBody Collection<ItemSizeDto> sizes) {
        return ResponseEntity.ok(itemService.updateSizes(itemId, sizes));
    }

    @DeleteMapping("/{id}/sizes")
    public ResponseEntity<Collection<ItemSizeDto>> removeSizeFromItem(@PathVariable("id") String itemId, @RequestParam String size) {
        return ResponseEntity.ok(itemService.deleteSize(itemId, size));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") String id) {
        itemService.delete(id);
    }
}
