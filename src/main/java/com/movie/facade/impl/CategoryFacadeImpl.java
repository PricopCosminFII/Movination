package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.CategoryConverter;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;
import com.movie.facade.CategoryFacade;
import com.movie.model.Category;
import com.movie.service.CategoryService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Setter
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {
    private CategoryService categoryService;
    private CategoryConverter categoryConverter;

    @Override
    public void save(CategoryDTO categoryDTO) throws NameFieldNull, ObjectAlreadyExists, ObjectNull {
        if (categoryDTO == null)
            throw new ObjectNull(MessageConstants.CATEGORY_DTO_NULL);
        if (categoryDTO.getName() == null)
            throw new NameFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
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
    public List<CategoryDTO> getAllCategoriesFromMovie(MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, IdFieldNull {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        return getCategoryDTOS(categoryService.getCategoriesByMovieName(movieDTO.getId()));
    }
}
