package com.sid.service;

import com.sid.entities.Item;
import com.sid.entities.Image;
import com.sid.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicesImpl implements Services {

    private final ItemRepository itemRepository;
    private final ImageService imageService;

    public ServicesImpl(ItemRepository itemRepository,
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
    public Item getItem(String id) {
        Item item = itemRepository.findById(id).get();
        if(item.getCode().isEmpty())
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
        if(!itemRepository.existsById(item.getIdItem()))
            throw new RuntimeException("This article doesn't exist!");

        if(item.getIdItem() == null)
            item.setIdItem(itemRepository.findItemByCodeEquals(item.getCode()).getIdItem());

        imageService.updateImage(item.getCode(), item.getImage());
        return itemRepository.save(item);
    }

    @Override
    public void delete(String id) {
        if(!itemRepository.existsById(id))
            throw new RuntimeException("This article does not exist!");

        Item item = itemRepository.findById(id).get();
        imageService.deleteImage(item.getCode());
        itemRepository.delete(item);
    }

}
