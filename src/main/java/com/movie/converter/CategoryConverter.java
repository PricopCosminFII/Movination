package com.movie.converter;

import com.movie.dto.CategoryDTO;
import com.movie.model.Category;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
public class CategoryConverter {
    private ModelMapper modelMapper;

    public Category convert(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            if (categoryDTO.getId() != null)
                categoryDTO.setId(null);
            return modelMapper.map(categoryDTO, Category.class);
        }
        return null;
    }

    public CategoryDTO convert(Category category) {
        if (category != null) {
            return modelMapper.map(category, CategoryDTO.class);
        }
        return null;
    }
}
