package com.movie.controller;

import com.movie.constants.NagivationConstants;
import com.movie.dto.MovieDTO;
import com.movie.exception.ObjectNotFound;
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
public class SearchController {
    MovieFacade movieFacade;

    @SneakyThrows
    @GetMapping(path = "/search")
    public String searchMovie(@RequestParam String searchTerm, ModelMap model) {
        model.addAttribute("searchTerm", searchTerm);
        try {
            List<MovieDTO> movieBySearch = movieFacade.getMovieBySearch(searchTerm);
            model.addAttribute("movies", movieBySearch);
            model.addAttribute("count", movieBySearch.size());
            return NagivationConstants.REDIRECT_TO_SEARCH;
        } catch (ObjectNotFound objectNotFound) {
            model.addAttribute("count", 0);
            return NagivationConstants.REDIRECT_TO_SEARCH;
        }
    }
}
