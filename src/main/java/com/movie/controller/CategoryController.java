package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.exception.ObjectNull;
import com.movie.exception.RequiredFieldNull;
import com.movie.facade.CategoryFacade;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getAllCategories() throws JsonProcessingException {
        List<CategoryDTO> categoryDTOList = categoryFacade.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(categoryDTOList));
    }

    @PostMapping("/category")
    @ResponseBody
    public ResponseEntity<String> save(@ModelAttribute CategoryDTO categoryDTO) throws JsonProcessingException {

        try {
            categoryFacade.save(categoryDTO);
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFUL_CREATION_CATEGORY);
        } catch (ObjectAlreadyExists | ObjectNull | RequiredFieldNull e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/categories/movie")
    @ResponseBody
    public ResponseEntity<String> getCategoriesByMovie(@ModelAttribute MovieDTO movieDTO) throws JsonProcessingException {
        List<CategoryDTO> categoryDTOList;
        try {
            categoryDTOList = categoryFacade.getAllCategoriesFromMovie(movieDTO);
        } catch (ObjectNull | ObjectNotFound | RequiredFieldNull e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(categoryDTOList));
    }
}
