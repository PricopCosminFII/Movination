package com.movie.facade;

import com.movie.dto.CategoryDTO;
import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;

import java.util.List;

public interface MovieFacade {
    void save(MovieDTO movieDTO) throws ObjectNull, NameFieldNull, ObjectAlreadyExists, InvalidData;

    List<MovieDTO> getAllMovies();

    List<MovieDTO> getMoviesByCategory(CategoryDTO categoryDTO) throws ObjectNull, NameFieldNull, ObjectNotFound;

    MovieDTO getMovieByItem(ItemDTO itemDTO) throws ObjectNull, IdFieldNull, ObjectNotFound;

    void addCategoriesToMovie(MovieDTO movieDTO, List<CategoryDTO> categoryDTOS) throws ObjectNull, NameFieldNull, ObjectNotFound, ObjectAlreadyExists, IdFieldNull;
}
