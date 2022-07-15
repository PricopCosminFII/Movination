package com.movie.repository;

import com.movie.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long>, CrudRepository<Category, Long> {
    Category findCategoryByName(String name);
}
