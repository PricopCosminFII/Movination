package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistDTO;
import com.movie.exception.*;
import com.movie.facade.ItemFacade;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Setter
public class ItemController {
    private ObjectMapper objectMapper;
    private ItemFacade itemFacade;

    @PostMapping("/rating/movie/watchlist")
    @ResponseBody
    public String addRatingToMovieFromWatchlist(@RequestParam Double rating, @RequestBody MovieDTO movieDTO, @ModelAttribute WatchlistDTO watchlistDTO) {
        try {
            itemFacade.addRatingToMovieFromWatchlist(rating, movieDTO, watchlistDTO);
            return MessageConstants.SUCCESSFUL_ADDING_RATING_TO_MOVIE;
        } catch (ObjectNull | IdFieldNull | NameFieldNull | InvalidData | ObjectNotFound e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/movie/watchlist")
    @ResponseBody
    public String removeMovieFromWatchlist(@RequestBody MovieDTO movieDTO, @ModelAttribute WatchlistDTO watchlistDTO) {
        try {
            itemFacade.removeMovieFromWatchlist(movieDTO, watchlistDTO);
            return MessageConstants.SUCCESSFUL_REMOVING_MOVIE_FROM_WATCHLIST;
        } catch (ObjectNull | IdFieldNull | NameFieldNull | ObjectNotFound e) {
            return e.getMessage();
        }
    }

    @PostMapping("/movie/watchlist/add")
    @ResponseBody
    public String addMovieToWatchlist(@RequestBody MovieDTO movieDTO, @ModelAttribute WatchlistDTO watchlistDTO) {
        try {
            itemFacade.addMovieToWatchlist(movieDTO, watchlistDTO);
            return MessageConstants.SUCCESSFUL_ADDING_MOVIE_TO_WATCHLIST;
        } catch (ObjectNull | IdFieldNull | NameFieldNull | ObjectNotFound | ObjectAlreadyExists e) {
            return e.getMessage();
        }
    }

    @GetMapping("/items")
    @ResponseBody
    public String getItemsFromWatchlist(@ModelAttribute WatchlistDTO watchlistDTO) throws JsonProcessingException {
        try {
            List<ItemDTO> itemDTOS = itemFacade.getItemsFromWatchlist(watchlistDTO);
            return objectMapper.writeValueAsString(itemDTOS);
        } catch (ObjectNull | IdFieldNull | ObjectNotFound e) {
            return e.getMessage();
        }
    }
}
