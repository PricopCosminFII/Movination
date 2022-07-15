package com.movie.facade;

import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistDTO;
import com.movie.exception.*;

import java.util.List;

public interface ItemFacade {
    void addMovieToWatchlist(MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, NameFieldNull, IdFieldNull, ObjectNotFound, ObjectAlreadyExists;

    void removeMovieFromWatchlist(MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, NameFieldNull, IdFieldNull, ObjectNotFound;

    void addRatingToMovieFromWatchlist(Double rating, MovieDTO movieDTO, WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, NameFieldNull, InvalidData, ObjectNotFound;

    List<ItemDTO> getItemsFromWatchlist(WatchlistDTO watchlistDTO) throws ObjectNull, IdFieldNull, ObjectNotFound;
}
