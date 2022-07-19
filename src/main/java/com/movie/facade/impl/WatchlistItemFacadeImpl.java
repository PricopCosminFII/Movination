package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.MovieConverter;
import com.movie.converter.WatchlistItemConverter;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;
import com.movie.facade.WatchlistItemFacade;
import com.movie.model.WatchlistItem;
import com.movie.service.WatchlistItemService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Setter
@Transactional
public class WatchlistItemFacadeImpl implements WatchlistItemFacade {
    private WatchlistItemService watchlistItemService;
    private WatchlistItemConverter watchlistItemConverter;
    private MovieConverter movieConverter;

    @Override
    public void addMovieToWatchlist(MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, ObjectNotFound, ObjectAlreadyExists, RequiredFieldNull {
        verify(movieDTO, userDTO);
        watchlistItemService.addMovieToWatchlistOfUser(userDTO.getEmail(), movieDTO.getId());
    }

    @Override
    public void removeMovieFromWatchlist(MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        verify(movieDTO, userDTO);
        watchlistItemService.removeMovieFromWatchlistOfUser(userDTO.getEmail(), movieDTO.getId());

    }

    private void verify(MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, RequiredFieldNull {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new RequiredFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        if (userDTO == null)
            throw new ObjectNull(MessageConstants.USER_DTO_NULL);
        if (userDTO.getEmail() == null)
            throw new RequiredFieldNull(MessageConstants.EMAIL_FIELD_OF_USER_DTO_NULL);
    }

    @Override
    public void addRatingToMovieFromWatchlist(Double rating, MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, InvalidData, ObjectNotFound, RequiredFieldNull {
        if (rating == null)
            throw new ObjectNull(MessageConstants.RATING_NULL);
        verify(movieDTO, userDTO);
        watchlistItemService.addRatingToMovieFromWatchlistOfUser(rating, movieDTO.getId(), userDTO.getEmail());
    }

    @Override
    public List<WatchlistItemDTO> getItemsFromWatchlist(UserDTO userDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        if (userDTO == null)
            throw new ObjectNull(MessageConstants.USER_DTO_NULL);
        if (userDTO.getEmail() == null)
            throw new RequiredFieldNull(MessageConstants.EMAIL_FIELD_OF_USER_DTO_NULL);
        List<WatchlistItem> watchlistItems = watchlistItemService.getItemsFromWatchlistOfUser(userDTO.getEmail());
        if (watchlistItems != null && !watchlistItems.isEmpty()) {
            List<WatchlistItemDTO> watchlistItemDTOS = new ArrayList<>();
            for (WatchlistItem watchlistItem : watchlistItems)
                watchlistItemDTOS.add(watchlistItemConverter.convert(watchlistItem));
            return watchlistItemDTOS;
        }
        return null;
    }

    @Override
    public Map<MovieDTO, WatchlistItemDTO> getContentOfWatchlist(UserDTO userDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        Map<MovieDTO, WatchlistItemDTO> watchlistContent = new TreeMap<>(Comparator.comparing(MovieDTO::getName));
        if (userDTO == null)
            throw new ObjectNull(MessageConstants.USER_DTO_NULL);
        if (userDTO.getEmail() == null)
            throw new RequiredFieldNull(MessageConstants.EMAIL_FIELD_OF_USER_DTO_NULL);
        List<WatchlistItem> itemsOfWatchlist = watchlistItemService.getItemsFromWatchlistOfUser(userDTO.getEmail());
        if (itemsOfWatchlist != null && !itemsOfWatchlist.isEmpty()) {
            for (WatchlistItem watchlistItem : itemsOfWatchlist) {
                watchlistContent.put(movieConverter.convert(watchlistItem.getMovie()), watchlistItemConverter.convert(watchlistItem));
            }

            return watchlistContent;
        }
        return null;
    }

    @Override
    public boolean isInWatchlistOfUser(UserDTO userDTO, MovieDTO movieDTO) throws ObjectNotFound, ObjectNull, RequiredFieldNull {
        verify(movieDTO, userDTO);
        return watchlistItemService.isInWatchlistOfUser(userDTO.getEmail(), movieDTO.getId());
    }
}
