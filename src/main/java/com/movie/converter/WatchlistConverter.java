package com.movie.converter;

import com.movie.dto.WatchlistDTO;
import com.movie.model.Watchlist;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
public class WatchlistConverter {
    private ModelMapper modelMapper;


    public WatchlistDTO convert(Watchlist watchlist) {
        if (watchlist != null) {
            return modelMapper.map(watchlist, WatchlistDTO.class);
        }
        return null;
    }
}
