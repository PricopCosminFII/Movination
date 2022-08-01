package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;
import com.movie.facade.MovieFacade;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Setter
public class MovieController {
    private ObjectMapper objectMapper;
    private MovieFacade movieFacade;

    @PostMapping(value = "/movie", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody MovieDTO movieDTO) throws JsonProcessingException {

        try {
            movieFacade.save(movieDTO);
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"" + MessageConstants.SUCCESSFUL_CREATION_MOVIE + "\"}");
        } catch (ObjectNull | RequiredFieldNull | ObjectAlreadyExists | InvalidData e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/movie/categories/add")
    @ResponseBody
    public ResponseEntity<String> addCategoriesToMovie(@ModelAttribute MovieDTO movieDTO, @RequestBody List<CategoryDTO> categoryDTOS) {

        try {
            movieFacade.addCategoriesToMovie(movieDTO, categoryDTOS);
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFUL_ADDING_CATEGORIES_TO_MOVIE);
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound | ObjectAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/movies")
    @ResponseBody
    public ResponseEntity<String> getAllMovies() throws JsonProcessingException {
        List<MovieDTO> movieDTOS = movieFacade.getAllMovies();
        return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(movieDTOS));
    }

    @GetMapping("/movies/category")
    @ResponseBody
    public ResponseEntity<String> getMoviesByCategory(@ModelAttribute CategoryDTO categoryDTO) throws JsonProcessingException {
        try {
            List<MovieDTO> movieDTOS = movieFacade.getMoviesByCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(movieDTOS));
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/movie/item")
    @ResponseBody
    public ResponseEntity<String> getMovieByItem(@ModelAttribute WatchlistItemDTO watchlistItemDTO) throws JsonProcessingException {
        try {
            MovieDTO movieDTO = movieFacade.getMovieByItem(watchlistItemDTO);
            return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(movieDTO));
        } catch (ObjectNull | ObjectNotFound | RequiredFieldNull e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
