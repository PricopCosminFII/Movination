package com.movie.repository;

import com.movie.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDAO extends JpaRepository<Item, Long>, CrudRepository<Item, Long> {
    Item findItemById(Long id);
}
