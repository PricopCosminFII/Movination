package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.ItemConverter;
import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistDTO;
import com.movie.exception.*;
import com.movie.facade.ItemFacade;
import com.movie.model.Item;
import com.movie.service.ItemService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Setter
@Transactional
public class ItemFacadeImpl implements ItemFacade {
    private ItemService itemService;
    private ItemConverter itemConverter;

    @Override
    public void addMovieToWatchlist(MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, ObjectNotFound, ObjectAlreadyExists {
        verify(movieDTO, watchlistDTO);
        itemService.addMovieToWatchlist(watchlistDTO.getId(), movieDTO.getId());
    }

    @Override
    public void removeMovieFromWatchlist(MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, ObjectNotFound {
        verify(movieDTO, watchlistDTO);
        itemService.removeMovieFromWatchlist(watchlistDTO.getId(), movieDTO.getId());

    }

    private void verify(MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        if (watchlistDTO == null)
            throw new ObjectNull(MessageConstants.WATCHLIST_DTO_NULL);
        if (watchlistDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_ITEM_DTO_NULL);
    }

    @Override
    public void addRatingToMovieFromWatchlist(Double rating, MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, InvalidData, ObjectNotFound {
        if (rating == null)
            throw new ObjectNull(MessageConstants.RATING_NULL);
        verify(movieDTO, watchlistDTO);
        itemService.addRatingToMovieFromWatchlist(rating, movieDTO.getId(), watchlistDTO.getId());
    }

    @Override
    public List<ItemDTO> getItemsFromWatchlist(WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, ObjectNotFound {
        if (watchlistDTO == null)
            throw new ObjectNull(MessageConstants.WATCHLIST_DTO_NULL);
        if (watchlistDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_WATCHLIST_DTO_NULL);
        List<Item> items = itemService.getItemsByIdWatchlist(watchlistDTO.getId());
        if (items != null && !items.isEmpty()) {
            List<ItemDTO> itemDTOS = new ArrayList<>();
            for (Item item : items)
                itemDTOS.add(itemConverter.convert(item));
            return itemDTOS;
        }
        return null;
    }
}
