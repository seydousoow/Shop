package com.sid.service.impl;

import com.sid.dto.ItemFiltersDto;
import com.sid.dto.ItemSizeDto;
import com.sid.entities.Item;
import com.sid.exception.RestException;
import com.sid.repositories.ItemRepository;
import com.sid.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.text.WordUtils.capitalizeFully;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String ITEM_NOT_FOUND = "This article could not be found!";
    private final ItemRepository itemRepository;

    @Override
    public Page<Item> getItems(String category, List<String> brands, List<String> sizes, Pageable pageable) {
        if (isEmpty(brands) && isEmpty(sizes)) return itemRepository.findItemsByCategoryEquals(category, pageable);
        if (isNotEmpty(brands) && isEmpty(sizes)) return itemRepository.findItemsByCategoryEqualsAndBrandIsIn(category, brands, pageable);
        if (isEmpty(brands) && isNotEmpty(sizes)) return itemRepository.findItemsByCategoryAndSizesIn(category, sizes, pageable);
        return itemRepository.findByCategoryAndBrandInAndSizesIn(category, brands, sizes, pageable);
    }

    @Override
    public List<String> getAllBrands(String category) {
        return itemRepository.findByCategory(category)
                .stream()
                .map(Item::getBrand)
                .filter(Objects::nonNull)
                .distinct()
                .sorted()
                .toList();
    }

    @Override
    public Item getItemByCode(String code) {
        return itemRepository.findItemByCodeEquals(code).orElseThrow(() -> new RestException(ITEM_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Item add(Item item) {
        item.setBrand(capitalizeFully(item.getBrand()));
        item.setDescription(capitalizeFully(item.getDescription()));
        item.setPicture(item.getCode().concat("-").concat(String.valueOf(currentTimeMillis())));
//        imageService.save(new Image(null, item.getCode(), item.getImage()));
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        if (!itemRepository.existsById(item.getIdItem()))
            throw new RestException("This article doesn't exist!");

        if (item.getIdItem() == null) item.setIdItem(getItemByCode(item.getCode()).getIdItem());

        item.setBrand(capitalizeFully(item.getBrand()));
        item.setDescription(capitalizeFully(item.getDescription()));

//        if (item.getImage() != null)
//            imageService.updateImage(item.getCode(), item.getImage());
        return itemRepository.save(item);
    }

    @Override
    public Collection<ItemSizeDto> addSizesToItem(String code, ItemSizeDto size) {
        checkIfSizeAreValid(size);
        var item = getItemByCode(code);
        if (Objects.isNull(item.getSizes())) item.setSizes(new ArrayList<>());
        item.getSizes().add(size);
        return itemRepository.save(item).getSizes();
    }

    private void checkIfSizeAreValid(ItemSizeDto size) {
        if (StringUtils.isBlank(size.getSize())) throw new RestException("The size should not be empty!");
        if (size.getQuantity() < 0) throw new RestException("The quantity should be greater or equal to zero!");
    }

    @Override
    public Collection<ItemSizeDto> updateSizes(String code, Collection<ItemSizeDto> sizes) {
        sizes.forEach(this::checkIfSizeAreValid);
        var item = getItemByCode(code);
        item.getSizes().clear();
        item.setSizes(sizes);
        return itemRepository.save(item).getSizes();
    }

    @Override
    public Collection<ItemSizeDto> deleteSize(String code, String size) {
        var item = getItemByCode(code);
        var sizes = item.getSizes();
        item.getSizes().clear();
        item.setSizes(sizes.stream().filter(x -> x.getSize().equalsIgnoreCase(size)).toList());
        return itemRepository.save(item).getSizes();
    }

    @Override
    public void delete(String id) {
        itemRepository.findById(id).ifPresentOrElse(itemRepository::delete, () -> {
            throw new RestException(ITEM_NOT_FOUND, HttpStatus.NOT_FOUND);
        });
    }

    @Override
    public ItemFiltersDto getItemsFilters(String category) {
        var result = itemRepository.findItemsFiltersByCategory(category);
        var filter = new ItemFiltersDto();
        result.forEach(r -> {
            r.getSizes()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(x -> x.getQuantity() > 0)
                    .map(ItemSizeDto::getSize)
                    .filter(Objects::nonNull)
                    .distinct()
                    .forEach(x -> filter.getSizes().add(x));
            if (isNotBlank(r.getBrand())) filter.getBrands().add(capitalizeFully(r.getBrand()));
        });
        return filter;
    }

}
