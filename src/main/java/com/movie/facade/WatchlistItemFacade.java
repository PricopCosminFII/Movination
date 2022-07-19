package com.movie.facade;

import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;

import java.util.List;
import java.util.Map;

public interface WatchlistItemFacade {
    void addMovieToWatchlist(MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, RequiredFieldNull, ObjectNotFound, ObjectAlreadyExists;

    void removeMovieFromWatchlist(MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, RequiredFieldNull, ObjectNotFound;

    void addRatingToMovieFromWatchlist(Double rating, MovieDTO movieDTO, UserDTO userDTO) throws ObjectNull, RequiredFieldNull, InvalidData, ObjectNotFound;

    List<WatchlistItemDTO> getItemsFromWatchlist(UserDTO userDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull;

    Map<MovieDTO, WatchlistItemDTO> getContentOfWatchlist(UserDTO userDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull;

    boolean isInWatchlistOfUser(UserDTO userDTO, MovieDTO movieDTO) throws ObjectNotFound, ObjectNull, RequiredFieldNull;
}
