package com.movie.controller;

import com.movie.constants.MessageConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectNotFound;
import com.movie.facade.CategoryFacade;
import com.movie.facade.MovieFacade;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Setter
public class AdminController {
    private MovieFacade movieFacade;
    private CategoryFacade categoryFacade;


    @SneakyThrows
    @GetMapping("/admin")
    public String admin(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        try {
            List<MovieDTO> movieDTOS;
            List<CategoryDTO> categoryDTOS = categoryFacade.getAllCategories();
            if (categoryDTOS != null && !categoryDTOS.isEmpty())
                model.addAttribute("adminCategories", categoryDTOS);
            if (categoryDTO.getName() == null || categoryDTO.getName().equals("All"))
                movieDTOS = movieFacade.getAllMovies();
            else {
                movieDTOS = movieFacade.getMoviesByCategory(categoryDTO);
            }
            if (movieDTOS != null && !movieDTOS.isEmpty()) {
                Map<MovieDTO, List<CategoryDTO>> moviesWithCategories = categoryFacade.getAllCategoriesForMovies(movieDTOS);
                if (moviesWithCategories != null && !moviesWithCategories.isEmpty())
                    model.addAttribute("adminMovies", moviesWithCategories);
            } else
                model.addAttribute("adminError", MessageConstants.NO_MOVIES_FOR_CATEGORY);
        } catch (ObjectNotFound e) {
            model.addAttribute("adminError", MessageConstants.CATEGORY_NOT_FOUND);
        }
        return "admin";
    }

    @DeleteMapping("/movie")
    @ResponseBody
    public ResponseEntity<String> deleteMovie(@ModelAttribute MovieDTO movie) {
        try {
            movieFacade.deleteMovie(movie);
        } catch (ObjectNotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
         return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESS);
    }

}