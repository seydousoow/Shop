package com.sid.service;

import com.sid.entities.Image;
import com.sid.entities.Item;
import com.sid.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ImageService imageService;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ImageService imageService) {
        this.itemRepository = itemRepository;
        this.imageService = imageService;
    }

    @Override
    public Page<Item> getItems(String category, Pageable pageable) {
        return itemRepository.findItemsByCategoryEquals(category, pageable);
    }

    @Override
    public Page<Item> getItemsByBrand(String category, List<String> brands, Pageable pageable) {
        return itemRepository.findItemsByCategoryEqualsAndBrandIsIn(category, brands, pageable);
    }

    @Override
    public List<String> getAllBrands(String category) {
        List<String> brands = new ArrayList<>();
        itemRepository.findByCategoryEquals(category).forEach(item -> brands.add(item.getBrand()));

        return brands.stream().distinct().sorted().collect(Collectors.toList());
    }

    @Override
    public Item getItem(String code) {
        Item item = itemRepository.findItemByCodeEquals(code);
        if (item == null)
            throw new RuntimeException("This article doesn't exist!");
        return item;
    }

    @Override
    public Item add(Item item) {
        imageService.save(new Image(null, item.getCode(), item.getImage()));
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        if (!itemRepository.existsById(item.getIdItem()))
            throw new RuntimeException("This article doesn't exist!");

        if (item.getIdItem() == null)
            item.setIdItem(itemRepository.findItemByCodeEquals(item.getCode()).getIdItem());

        if (item.getImage() != null)
            imageService.updateImage(item.getCode(), item.getImage());
        return itemRepository.save(item);
    }

    @Override
    public void delete(String id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty())
            throw new RuntimeException("This article does not exist!");

        Item item = optionalItem.get();
        itemRepository.delete(item);
    }

}
