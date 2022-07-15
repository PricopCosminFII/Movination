package com.movie.service;

import com.movie.model.Watchlist;

import java.util.List;

public interface WatchlistService {
    void create();

    List<Watchlist> getAllWatchlist();

}
