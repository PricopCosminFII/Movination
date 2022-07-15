package com.movie.service;

import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItemsByIdWatchlist(Long idWatchlist) throws ObjectNotFound;

    void addMovieToWatchlist(Long idWatchlist, Long idMovie) throws ObjectNotFound, ObjectAlreadyExists;

    void addRatingToMovieFromWatchlist(Double rating, Long idMovie, Long idWatchlist) throws InvalidData, ObjectNotFound;

    void removeMovieFromWatchlist(Long idWatchlist, Long idMovie) throws ObjectNotFound;
}
