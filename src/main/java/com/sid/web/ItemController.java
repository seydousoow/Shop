package com.sid.web;

import com.sid.entities.Item;
import com.sid.service.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class ItemController {

    private final Services services;

    public ItemController(Services services) {
        this.services = services;
    }

    @GetMapping(value = "/items/{category}")
    public Page<Item> getItems(@PathVariable String category,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "24") int size,
                               @RequestParam(value = "sort", defaultValue = "addedAt") String sortBy,
                               @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        return services.getItems(category, setPagination(page, size, sortBy, direction));
    }

    @GetMapping(value = "/items/{category}/{id}")
    public Item getItem(@PathVariable String category,
                        @PathVariable("id") String id) { return services.getItem(id);}

    @GetMapping(value = "/items/{category}/brand")
    public Page<Item> getItemsByBrand(@PathVariable String category,
                                      @RequestParam(name = "brands") String brands,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "24") int size,
                                      @RequestParam(value = "sort", defaultValue = "addedAt") String sortBy,
                                      @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        var listBrands = new AtomicReference<>(Arrays.asList(new String(Base64.getUrlDecoder().decode(brands)).split("%")));
        return services.getItemsByBrand(category, listBrands.get(), setPagination(page, size, sortBy, direction));
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) { return services.add(item);}

    @PutMapping("/items")
    public Item updateItem(@RequestBody Item item) { return services.update(item);}

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable("id") String id) { services.delete(id);}

    private static Pageable setPagination(int page, int size, String sortBy, String direction){
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        Sort sort = Sort.by(dir, sortBy);
        return PageRequest.of(page, size, sort);
    }
}
