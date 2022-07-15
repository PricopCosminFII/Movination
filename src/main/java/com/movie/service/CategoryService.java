package com.movie.service;

import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Category;

import java.util.List;

public interface CategoryService {
    void save(Category category) throws ObjectAlreadyExists;

    List<Category> getCategoriesByMovieName(Long idMovie) throws ObjectNotFound;

    List<Category> getAllCategories();
}
