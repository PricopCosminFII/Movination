package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.CategoryConverter;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.exception.ObjectNull;
import com.movie.exception.RequiredFieldNull;
import com.movie.facade.CategoryFacade;
import com.movie.model.Category;
import com.movie.service.CategoryService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Setter
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {
    private CategoryService categoryService;
    private CategoryConverter categoryConverter;

    @Override
    public void save(CategoryDTO categoryDTO) throws RequiredFieldNull, ObjectAlreadyExists, ObjectNull {
        if (categoryDTO == null)
            throw new ObjectNull(MessageConstants.CATEGORY_DTO_NULL);
        if (categoryDTO.getName() == null)
            throw new RequiredFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
        categoryService.save(categoryConverter.convert(categoryDTO));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories != null && !categories.isEmpty()) {
            return getCategoryDTOS(categories);
        }
        return null;
    }

    private List<CategoryDTO> getCategoryDTOS(List<Category> categories) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : categories)
            categoryDTOS.add(categoryConverter.convert(category));
        return categoryDTOS;
    }

    @Override
    public List<CategoryDTO> getAllCategoriesFromMovie(MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new RequiredFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        return getCategoryDTOS(categoryService.getCategoriesByMovieName(movieDTO.getId()));
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryConverter.convert(categoryService.getCategoryByName(name));
    }

    @Override
    public Map<MovieDTO, List<CategoryDTO>> getAllCategoriesForMovies(List<MovieDTO> movies) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        Map<MovieDTO, List<CategoryDTO>> moviesWithCategories = new TreeMap<>(Comparator.comparing(MovieDTO::getId));
        if (movies != null && !movies.isEmpty()) {
            for (MovieDTO movieDTO : movies)
                moviesWithCategories.put(movieDTO, getAllCategoriesFromMovie(movieDTO));
            return moviesWithCategories;
        }
        return null;
    }
}
