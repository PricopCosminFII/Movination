package com.movie.facade.impl;

import com.movie.converter.WatchlistConverter;
import com.movie.dto.WatchlistDTO;
import com.movie.facade.WatchlistFacade;
import com.movie.model.Watchlist;
import com.movie.service.WatchlistService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Setter
@Transactional
public class WatchlistFacadeImpl implements WatchlistFacade {
    private WatchlistService watchlistService;
    private WatchlistConverter watchlistConverter;

    @Override
    public void create() {
        watchlistService.create();
    }

    @Override
    public List<WatchlistDTO> getAllWatchlist() {
        List<Watchlist> allWatchlist = watchlistService.getAllWatchlist();
        if (allWatchlist != null && !allWatchlist.isEmpty()) {
            List<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            for (Watchlist watchlist : allWatchlist)
                watchlistDTOS.add(watchlistConverter.convert(watchlist));
            return watchlistDTOS;
        }

        return null;
    }
}
