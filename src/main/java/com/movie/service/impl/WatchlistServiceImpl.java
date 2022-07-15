package com.movie.service.impl;

import com.movie.model.Watchlist;
import com.movie.repository.WatchlistDAO;
import com.movie.service.WatchlistService;
import lombok.Setter;

import java.util.List;

@Setter
public class WatchlistServiceImpl implements WatchlistService {
    private WatchlistDAO watchlistDAO;

    @Override
    public void create() {
        watchlistDAO.save(new Watchlist());
    }

    @Override
    public List<Watchlist> getAllWatchlist() {
        return watchlistDAO.findAll();
    }
}
