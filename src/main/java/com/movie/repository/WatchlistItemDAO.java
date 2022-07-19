package com.movie.repository;

import com.movie.model.WatchlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistItemDAO extends JpaRepository<WatchlistItem, Long>, CrudRepository<WatchlistItem, Long> {
    WatchlistItem findItemById(Long id);
}
