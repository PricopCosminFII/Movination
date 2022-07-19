package com.movie.service;

import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.WatchlistItem;

import java.util.List;

public interface WatchlistItemService {
    List<WatchlistItem> getItemsFromWatchlistOfUser(String email) throws ObjectNotFound;

    void addMovieToWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound, ObjectAlreadyExists;

    void addRatingToMovieFromWatchlistOfUser(Double rating, Long idMovie, String email) throws InvalidData, ObjectNotFound;

    void removeMovieFromWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound;

    boolean isInWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound;
}
