package com.movie.converter;

import com.movie.dto.MovieDTO;
import com.movie.model.Movie;
import org.modelmapper.ModelMapper;

public class MovieConverter {

    ModelMapper modelMapper;

    public Movie convert(MovieDTO movieDTO) {
        if (movieDTO != null) {
            Movie movie = modelMapper.map(movieDTO, Movie.class);
            return movie;
        }
        return null;
    }

    public MovieDTO convert(Movie movie) {
        if (movie != null) {
            MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
            return movieDTO;
        }
        return null;
    }

}
