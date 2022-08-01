package com.movie.facade;

import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;

import java.util.List;

public interface MovieFacade {
    void save(MovieDTO movieDTO) throws ObjectNull, RequiredFieldNull, ObjectAlreadyExists, InvalidData;

    List<MovieDTO> getAllMovies();

    List<MovieDTO> getMoviesByCategory(CategoryDTO categoryDTO) throws ObjectNull, RequiredFieldNull, ObjectNotFound;

    MovieDTO getMovieByItem(WatchlistItemDTO watchlistItemDTO) throws ObjectNull, RequiredFieldNull, ObjectNotFound;

    void addCategoriesToMovie(MovieDTO movieDTO, List<CategoryDTO> categoryDTOS) throws ObjectNull, RequiredFieldNull, ObjectNotFound, ObjectAlreadyExists;

    MovieDTO getById(Long id);

    List<MovieDTO> getMovieBySearch(String search) throws ObjectNotFound;

    void deleteMovie(MovieDTO movieDTO) throws ObjectNotFound;

    List<MovieDTO> getMovieRecommendation(List<CategoryDTO> categoryDTOList, MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull;

    String validateMovie(MovieDTO movieDTO);

    void update(Long id, String name, String description, Long minutes, String picture, Integer year) throws InvalidData, ObjectNotFound, ObjectNull;

    void deleteCategories(MovieDTO movieDTO, List<CategoryDTO> categories);

    List<CategoryDTO> setCategoriesToMovie(List<String> categories);
}
