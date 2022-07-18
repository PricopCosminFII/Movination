package com.movie.controller;

import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectNull;
import com.movie.facade.CategoryFacade;
import com.movie.facade.MovieFacade;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Setter
public class HomepageController {

    MovieFacade movieFacade;
    CategoryFacade categoryFacade;

    @GetMapping(path = "/")
    public String showHomepage(ModelMap model) {
        List<MovieDTO> list = movieFacade.getAllMovies();
        model.addAttribute("movies", list);
        return "index";
    }

    @SneakyThrows
    @GetMapping(path = "/details")
    public String movieDetails(@RequestParam Long id, ModelMap model) {
        try {
            MovieDTO movieDTO = movieFacade.getById(id);
            model.addAttribute("movieDetails", movieDTO);
            List<CategoryDTO> categoryDTOS = categoryFacade.getAllCategoriesFromMovie(movieDTO);
            model.addAttribute("moviecategory", categoryDTOS);
            return "details";
        } catch (ObjectNull objectNull) {
            return "redirect:/";
        }
    }
}
