package com.movie.service;

import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.exception.ObjectNull;
import com.movie.model.Category;
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

    void delete(Long idMovie) throws ObjectNotFound;

    void update(Long id, String name, String description, Long minutes, String picture, Integer year) throws InvalidData, ObjectNotFound, ObjectNull;

    void setCategories(Movie movie, List<Category> categories);

    void deleteCategories(Movie movie, List<Category> categories);
}
