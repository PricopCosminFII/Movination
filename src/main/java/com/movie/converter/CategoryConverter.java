package com.movie.converter;

import com.movie.dto.CategoryDTO;
import com.movie.model.Category;
import org.modelmapper.ModelMapper;

public class CategoryConverter {
    ModelMapper modelMapper;

    public Category convert(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {

            Category category = modelMapper.map(categoryDTO,Category.class);
            return category;
        }
        return null;
    }

    public CategoryDTO convert(Category category) {
        if (category != null) {
            CategoryDTO categoryDTO = modelMapper.map(category,CategoryDTO.class);
            return categoryDTO;
        }
        return null;
    }
}
