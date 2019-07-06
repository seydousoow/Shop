package com.sid.web;

import com.sid.entities.Item;
import com.sid.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /*
     * Get all items or items that belong to a selected list of brands
     */

    @GetMapping(value = "/items/{category}")
    public Page<Item> getItems(@PathVariable String category,
                               @RequestParam(name = "brands") List<String> brands,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "24") int size,
                               @RequestParam(value = "sort", defaultValue = "addedAt") String sortBy,
                               @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        category = category.substring(0, 1).toUpperCase() + category.substring(1);

        return brands.size() == 0 ? itemService.getItems(category, setPagination(page, size, sortBy, direction))
                : itemService.getItemsByBrand(category, brands, setPagination(page, size, sortBy, direction));
    }

    /*
     * Get one item that has the code attached to the path variable code
     */
    @GetMapping(value = "/items/{category}/{code}")
    public Item getItem(@PathVariable String category,
                        @PathVariable String code) {
        return itemService.getItem(code);
    }

    /*
     * Get all brands list of a category
     */
    @GetMapping(value = "/items/{category}/brand")
    public List<String> getAllBrands(@PathVariable String category) {
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        return itemService.getAllBrands(category);
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        return itemService.add(item);
    }

    @PutMapping("/items")
    public Item updateItem(@RequestBody Item item) {
        return itemService.update(item);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") String id) {
        itemService.delete(id);
    }

    private static Pageable setPagination(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        Sort sort = Sort.by(dir, sortBy);
        return PageRequest.of(page, size, sort);
    }
}
