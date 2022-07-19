package com.movie.converter;

import com.movie.dto.WatchlistItemDTO;
import com.movie.model.WatchlistItem;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
public class WatchlistItemConverter {
    private ModelMapper modelMapper;

    public WatchlistItem convert(WatchlistItemDTO watchlistItemDTO) {
        if (watchlistItemDTO != null) {
            if (watchlistItemDTO.getId() != null)
                watchlistItemDTO.setId(null);
            return modelMapper.map(watchlistItemDTO, WatchlistItem.class);
        }
        return null;
    }

    public WatchlistItemDTO convert(WatchlistItem watchlistItem) {
        if (watchlistItem != null) {
            return modelMapper.map(watchlistItem, WatchlistItemDTO.class);
        }
        return null;
    }
}
