package com.movie.converter;

import com.movie.dto.CategoryDTO;
import com.movie.model.Category;

public class CategoryConverter {
   public Category convert(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            return category;
        }
        return null;
    }

   public CategoryDTO convert(Category category) {
        if (category != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            return categoryDTO;
        }
        return null;
    }
}
