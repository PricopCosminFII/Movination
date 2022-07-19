package com.movie.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class WatchlistItemDTO {
    Long id;
    Double userRating;
}
