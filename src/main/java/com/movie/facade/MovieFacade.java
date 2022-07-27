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

    List<MovieDTO> getMovieRecommendation(List<CategoryDTO> categoryDTOList, MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull;
}
