package com.movie.converter;

import com.movie.dto.ItemDTO;
import com.movie.model.Item;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
public class ItemConverter {
    private ModelMapper modelMapper;

    public Item convert(ItemDTO itemDTO) {
        if (itemDTO != null) {
            if (itemDTO.getId() != null)
                itemDTO.setId(null);
            return modelMapper.map(itemDTO, Item.class);
        }
        return null;
    }

    public ItemDTO convert(Item item) {
        if (item != null) {
            return modelMapper.map(item, ItemDTO.class);
        }
        return null;
    }
}
