package com.movie.converter;

import com.movie.dto.MovieDTO;
import com.movie.model.Movie;

public class MovieConverter {


    public Movie convert(MovieDTO movieDTO) {
        if (movieDTO != null) {
            Movie movie = new Movie();
            movie.setName(movieDTO.getName());
            movie.setDescription(movieDTO.getDescription());
            movie.setPicture(movieDTO.getPicture());
            movie.setRating(movieDTO.getRating());
            return movie;
        }
        return null;
    }

    public MovieDTO convert(Movie movie) {
        if (movie != null) {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setName(movie.getName());
            movieDTO.setId(movie.getId());
            movieDTO.setDescription(movie.getDescription());
            movieDTO.setRating(movieDTO.getRating());
            movieDTO.setPicture(movie.getPicture());
            return movieDTO;
        }
        return null;
    }

}
