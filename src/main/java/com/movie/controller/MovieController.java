package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;
import com.movie.facade.MovieFacade;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Setter
public class MovieController {
    private ObjectMapper objectMapper;
    private MovieFacade movieFacade;

    @PostMapping("/movie")
    @ResponseBody
    public String save(@ModelAttribute MovieDTO movieDTO) throws JsonProcessingException {

        try {
            movieFacade.save(movieDTO);
            return MessageConstants.SUCCESSFUL_CREATION_MOVIE;
        } catch (ObjectNull | NameFieldNull | ObjectAlreadyExists | InvalidData e) {
            return e.getMessage();
        }
    }

    @PostMapping("/movie/categories/add")
    @ResponseBody
    public String addCategoriesToMovie(@ModelAttribute MovieDTO movieDTO, @RequestBody List<CategoryDTO> categoryDTOS) {

        try {
            movieFacade.addCategoriesToMovie(movieDTO, categoryDTOS);
            return MessageConstants.SUCCESSFUL_ADDING_CATEGORIES_TO_MOVIE;
        } catch (ObjectNull | NameFieldNull | ObjectNotFound | ObjectAlreadyExists | IdFieldNull e) {
            return e.getMessage();
        }
    }

    @GetMapping("/movies")
    @ResponseBody
    public String getAllMovies() throws JsonProcessingException {
        List<MovieDTO> movieDTOS = movieFacade.getAllMovies();
        return objectMapper.writeValueAsString(movieDTOS);
    }

    @GetMapping("/movies/category")
    @ResponseBody
    public String getMoviesByCategory(@ModelAttribute CategoryDTO categoryDTO) throws JsonProcessingException {
        try {
            List<MovieDTO> movieDTOS = movieFacade.getMoviesByCategory(categoryDTO);
            return objectMapper.writeValueAsString(movieDTOS);
        } catch (ObjectNull | NameFieldNull | ObjectNotFound e) {
            return e.getMessage();
        }

    }

    @GetMapping("/movie/item")
    @ResponseBody
    public String getMovieByItem(@ModelAttribute ItemDTO itemDTO) throws JsonProcessingException {
        try {
            MovieDTO movieDTO = movieFacade.getMovieByItem(itemDTO);
            return objectMapper.writeValueAsString(movieDTO);
        } catch (ObjectNull | IdFieldNull | ObjectNotFound e) {
            return e.getMessage();
        }

    }
}
