package com.movie.controller;

import com.movie.constants.MessageConstants;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;
import com.movie.facade.WatchlistItemFacade;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Setter
public class WatchlistItemController {
    private WatchlistItemFacade watchlistItemFacade;

    @PostMapping("/watchlist/rate/movie")
    @ResponseBody
    public String addRatingToMovieFromWatchlist(@RequestParam Double rating, @ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return MessageConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.addRatingToMovieFromWatchlist(rating, movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | InvalidData | ObjectNotFound e) {
            return MessageConstants.REDIRECT_TO_HOME;
        }
    }

    @GetMapping("/watchlist")
    public String getWatchlistContent(ModelMap modelMap, Authentication authentication) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        String email = authentication.getName();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        Map<MovieDTO, WatchlistItemDTO> watchlistContent = watchlistItemFacade.getContentOfWatchlist(userDTO);
        modelMap.addAttribute("watchlistContent", watchlistContent);
        return "watchlist";
    }

    @DeleteMapping("/watchlist/remove/movie")
    @ResponseBody
    public String removeMovieFromWatchlist(@ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return MessageConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.removeMovieFromWatchlist(movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound e) {
            return MessageConstants.REDIRECT_TO_HOME;
        }
    }

    @PostMapping("/watchlist/add/movie")
    @ResponseBody
    public String addMovieToWatchlist(@ModelAttribute MovieDTO movieDTO, Authentication authentication) {
        try {
            if (authentication == null)
                return MessageConstants.REDIRECT_TO_LOGIN;
            String email = authentication.getName();
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            watchlistItemFacade.addMovieToWatchlist(movieDTO, userDTO);
            return MessageConstants.SUCCESS;
        } catch (ObjectNull | RequiredFieldNull | ObjectNotFound | ObjectAlreadyExists e) {
            return MessageConstants.REDIRECT_TO_HOME;
        }
    }


}
