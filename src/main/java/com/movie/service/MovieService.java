package com.movie.service;

import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    void save(Movie movie) throws ObjectAlreadyExists, InvalidData;

    List<Movie> getAllMovies();

    List<Movie> getMovieByCategoryName(String categoryName) throws ObjectNotFound;

    void addCategoriesToMovie(Long idMovie, List<String> categoryNames) throws ObjectNotFound, ObjectAlreadyExists;

    Movie getMovieByItemId(Long id) throws ObjectNotFound;

    Movie getById(Long id);

    List<Movie> getMovieBySearch(String search) throws ObjectNotFound;
}
