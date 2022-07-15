package com.movie.converter;

import com.movie.dto.MovieDTO;
import com.movie.model.Movie;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter
public class MovieConverter {

    private ModelMapper modelMapper;

    public Movie convert(MovieDTO movieDTO) {
        if (movieDTO != null) {
            if (movieDTO.getId() != null)
                movieDTO.setId(null);
            return modelMapper.map(movieDTO, Movie.class);
        }
        return null;
    }

    public MovieDTO convert(Movie movie) {
        if (movie != null) {
            return modelMapper.map(movie, MovieDTO.class);
        }
        return null;
    }

}
