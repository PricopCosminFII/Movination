package com.movie.facade.impl;

import com.movie.converter.MovieConverter;
import com.movie.dto.MovieDTO;
import com.movie.facade.MovieFacade;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter
public class MovieFacadeImpl implements MovieFacade {
    MovieService movieService;
    MovieConverter movieConverter;

    @Override
    public List<MovieDTO> getAll() {
        List<Movie> movies = movieService.getAll();
        List<MovieDTO> moviesDTO = new ArrayList<>();
        for(Movie movie : movies) {
            moviesDTO.add(movieConverter.convert(movie));
        }
        return moviesDTO;
    }
}
