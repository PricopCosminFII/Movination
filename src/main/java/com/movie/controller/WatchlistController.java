package com.movie.controller;

import com.movie.constants.MessageConstants;
import com.movie.facade.WatchlistFacade;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Setter
public class WatchlistController {
    private WatchlistFacade watchlistFacade;

    @PostMapping("/watchlist")
    @ResponseBody
    public ResponseEntity<String> create() {
        try {
            watchlistFacade.create();
            return ResponseEntity.status(HttpStatus.OK).body(MessageConstants.SUCCESSFUL_CREATION_CATEGORY);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
