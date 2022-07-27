package com.movie.controller;

import com.movie.constants.MessageConstants;
import com.movie.constants.NavigationConstants;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;
import com.movie.facade.UserFacade;
import com.movie.facade.WatchlistItemFacade;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@Setter
public class WatchlistItemController {
    private WatchlistItemFacade watchlistItemFacade;
    private UserFacade userFacade;

    @PostMapping("/watchlist/rate/movie")
    @ResponseBody
    public String addRatingToMovieFromWatchlist(@RequestParam Double rating, @ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return NavigationConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.addRatingToMovieFromWatchlist(rating, movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | InvalidData | ObjectNotFound e) {
            return NavigationConstants.REDIRECT_TO_HOME;
        }
    }

    @GetMapping("/watchlist")
    public String getWatchlistContent(ModelMap model, Authentication authentication, HttpServletResponse response) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        UserDTO user = userFacade.getUserByEmail(authentication.getName());
        Cookie cookie = new Cookie("name", user.getFirstName());
        cookie.setPath("/");
        response.addCookie(cookie);
        model.addAttribute("name", user.getFirstName());
        Map<MovieDTO, WatchlistItemDTO> watchlistContent = watchlistItemFacade.getContentOfWatchlist(user);
        model.addAttribute("watchlistContent", watchlistContent);
        return "watchlist";
    }

    @DeleteMapping("/watchlist/remove/movie")
    @ResponseBody
    public String removeMovieFromWatchlist(@ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return NavigationConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.removeMovieFromWatchlist(movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound e) {
            return NavigationConstants.REDIRECT_TO_HOME;
        }
    }

    @PostMapping("/watchlist/add/movie")
    @ResponseBody
    public String addMovieToWatchlist(@ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return NavigationConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.addMovieToWatchlist(movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound | ObjectAlreadyExists e) {
            return NavigationConstants.REDIRECT_TO_HOME;
        }
    }


}
