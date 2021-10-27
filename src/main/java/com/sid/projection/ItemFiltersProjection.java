package com.sid.projection;

import com.sid.dto.ItemSizeDto;
import com.sid.entities.Item;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(types = Item.class)
public interface ItemFiltersProjection {
    Collection<ItemSizeDto> getSizes();

    String getBrand();
}
