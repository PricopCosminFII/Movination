package com.movie.facade;

import com.movie.dto.WatchlistDTO;

import java.util.List;

public interface WatchlistFacade {
    void create();

    List<WatchlistDTO> getAllWatchlist();
}
