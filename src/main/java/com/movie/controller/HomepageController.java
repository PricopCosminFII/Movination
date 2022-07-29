package com.movie.controller;

import com.google.protobuf.Message;
import com.movie.constants.MessageConstants;
import com.movie.constants.NavigationConstants;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.exception.ObjectNotFound;
import com.movie.exception.ObjectNull;
import com.movie.facade.CategoryFacade;
import com.movie.facade.MovieFacade;
import com.movie.facade.UserFacade;
import com.movie.facade.WatchlistItemFacade;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Setter
public class HomepageController {
    UserFacade userFacade;
    MovieFacade movieFacade;
    CategoryFacade categoryFacade;
    WatchlistItemFacade watchlistItemFacade;

    @GetMapping(path = "/")
    public String showHomepage(ModelMap model, Authentication authentication, HttpServletResponse response) {
        List<MovieDTO> movies = movieFacade.getAllMovies();
        List<CategoryDTO> categories = categoryFacade.getAllCategories();
        model.addAttribute("movies", movies);
        model.addAttribute("categories", categories);
        if (authentication != null) {
            UserDTO user = userFacade.getUserByEmail(authentication.getName());
            if (user != null) {
                Cookie cookie = new Cookie("name", user.getFirstName());
                cookie.setPath("/");
                response.addCookie(cookie);
                model.addAttribute("name", user.getFirstName());
            }
        }

        return "index";
    }

    @SneakyThrows
    @GetMapping(path = "/details")
    public String movieDetails(@RequestParam Long id, ModelMap model, Authentication authentication) {
        try {

            MovieDTO movieDTO = movieFacade.getById(id);
            model.addAttribute("movieDetails", movieDTO);
            List<CategoryDTO> categoryDTOS = categoryFacade.getAllCategoriesFromMovie(movieDTO);
            model.addAttribute("moviecategory", categoryDTOS);
            List<MovieDTO> movieRecommendation = movieFacade.getMovieRecommendation(categoryDTOS,movieDTO);
            model.addAttribute("moviesRecommendation",movieRecommendation);
            if (authentication != null) {
                String email = authentication.getName();
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(email);
                if (watchlistItemFacade.isInWatchlistOfUser(userDTO, movieDTO))
                    model.addAttribute("isInWatchlist", true);
            }
            return NavigationConstants.REDIRECT_TO_DETAILS;
        } catch (ObjectNull objectNull) {
            return NavigationConstants.REDIRECT_TO_HOME;
        } catch (ObjectNotFound objectNotFound) {
            return NavigationConstants.REDIRECT_TO_DETAILS;
        }
    }

    @SneakyThrows
    @GetMapping(path = "/category")
    public String getMoviesByCategory(@RequestParam String name, ModelMap model) {
        try {
            CategoryDTO categoryDTO = categoryFacade.getCategoryByName(name);
            List<CategoryDTO> categories = categoryFacade.getAllCategories();
            List<MovieDTO> movieDTOS = movieFacade.getMoviesByCategory(categoryDTO);
            if(movieDTOS == null) {
                model.addAttribute("error", MessageConstants.NO_MOVIES_FOR_CATEGORY);
            }
            model.addAttribute("movies", movieDTOS);
            model.addAttribute("categories", categories);
            return "index";
        } catch (ObjectNull objectNull) {
            return "redirect:/";
        }
    }
}
