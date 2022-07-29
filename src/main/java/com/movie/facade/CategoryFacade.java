package com.movie.facade;

import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.exception.ObjectNull;
import com.movie.exception.RequiredFieldNull;

import java.util.List;
import java.util.Map;

public interface CategoryFacade {
    void save(CategoryDTO categoryDTO) throws RequiredFieldNull, ObjectAlreadyExists, ObjectNull;

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getAllCategoriesFromMovie(MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull;

    CategoryDTO getCategoryByName(String name);

    Map<MovieDTO, List<CategoryDTO>> getAllCategoriesForMovies(List<MovieDTO> movies) throws ObjectNull, ObjectNotFound, RequiredFieldNull;
}
