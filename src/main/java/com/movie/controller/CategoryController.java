package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;
import com.movie.facade.CategoryFacade;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Setter
public class CategoryController {
    private ObjectMapper objectMapper;
    private CategoryFacade categoryFacade;

    @GetMapping("/categories")
    @ResponseBody
    public String getAllCategories() throws JsonProcessingException {
        List<CategoryDTO> categoryDTOList = categoryFacade.getAllCategories();
        return objectMapper.writeValueAsString(categoryDTOList);
    }

    @PostMapping("/category")
    @ResponseBody
    public String save(@ModelAttribute CategoryDTO categoryDTO) throws JsonProcessingException {

        try {
            categoryFacade.save(categoryDTO);
            return MessageConstants.SUCCESSFUL_CREATION_CATEGORY;
        } catch (ObjectAlreadyExists | ObjectNull | NameFieldNull e) {
            return e.getMessage();
        }
    }

    @GetMapping("/categories/movie")
    @ResponseBody
    public String getCategoriesByMovie(@ModelAttribute MovieDTO movieDTO) throws JsonProcessingException {
        List<CategoryDTO> categoryDTOList;
        try {
            categoryDTOList = categoryFacade.getAllCategoriesFromMovie(movieDTO);
        } catch (ObjectNull | ObjectNotFound | IdFieldNull e) {
            return e.getMessage();
        }
        return objectMapper.writeValueAsString(categoryDTOList);
    }
}
