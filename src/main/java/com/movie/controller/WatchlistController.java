package com.movie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.constants.MessageConstants;
import com.movie.dto.WatchlistDTO;
import com.movie.facade.WatchlistFacade;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Setter
public class WatchlistController {
    private ObjectMapper objectMapper;
    private WatchlistFacade watchlistFacade;

    @PostMapping("/watchlist")
    @ResponseBody
    public String create() {
        try {
            watchlistFacade.create();
            return MessageConstants.SUCCESSFUL_CREATION_WATCHLIST;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/watchlist")
    @ResponseBody
    public String getAllWatchlist() throws JsonProcessingException {
        List<WatchlistDTO> watchlistDTOS = watchlistFacade.getAllWatchlist();
        return objectMapper.writeValueAsString(watchlistDTOS);
    }
}
