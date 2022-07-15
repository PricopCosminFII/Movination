package com.movie.facade;

import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;

import java.util.List;

public interface CategoryFacade {
    void save(CategoryDTO categoryDTO) throws NameFieldNull, ObjectAlreadyExists, ObjectNull;

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getAllCategoriesFromMovie(MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, IdFieldNull;
}
