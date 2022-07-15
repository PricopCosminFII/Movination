package com.movie.controller;

import com.movie.dto.MovieDTO;
import com.movie.facade.MovieFacade;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Setter
public class HomepageController {

    MovieFacade movieFacade;

    @GetMapping(path = "/")
    public String showHomepage(ModelMap model) {
        List<MovieDTO> list= movieFacade.getAllMovies();
        model.addAttribute("movies", list);
        return "index";
    }
}
