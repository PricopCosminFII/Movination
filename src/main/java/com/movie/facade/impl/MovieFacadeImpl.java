package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.MovieConverter;
import com.movie.dto.CategoryDTO;
import com.movie.dto.ItemDTO;
import com.movie.dto.MovieDTO;
import com.movie.exception.*;
import com.movie.facade.MovieFacade;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Setter
public class MovieFacadeImpl implements MovieFacade {
    private MovieService movieService;
    private MovieConverter movieConverter;

    @Override
    @Transactional
    public void save(MovieDTO movieDTO) throws ObjectNull, NameFieldNull, ObjectAlreadyExists, InvalidData {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getName() == null)
            throw new NameFieldNull(MessageConstants.NAME_FIELD_OF_MOVIE_DTO_NULL);
        movieService.save(movieConverter.convert(movieDTO));

    }

    @Override
    @Transactional
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        if (movies != null && !movies.isEmpty()) {
            return getMovieDTOS(movies);
        }
        return null;
    }

    private List<MovieDTO> getMovieDTOS(List<Movie> movies) {
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies)
            movieDTOS.add(movieConverter.convert(movie));
        return movieDTOS;
    }

    @Override
    @Transactional
    public List<MovieDTO> getMoviesByCategory(CategoryDTO categoryDTO) throws ObjectNull, NameFieldNull, ObjectNotFound {
        if (categoryDTO == null)
            throw new ObjectNull(MessageConstants.CATEGORY_DTO_NULL);
        if (categoryDTO.getName() == null)
            throw new NameFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
        List<Movie> movies = movieService.getMovieByCategoryName(categoryDTO.getName());
        if (movies != null && !movies.isEmpty())
            return getMovieDTOS(movies);
        return null;
    }

    @Override
    @Transactional
    public MovieDTO getMovieByItem(ItemDTO itemDTO) throws ObjectNull, IdFieldNull, ObjectNotFound {
        if (itemDTO == null)
            throw new ObjectNull(MessageConstants.ITEM_DTO_NULL);
        if (itemDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_ITEM_DTO_NULL);
        Movie movie = movieService.getMovieByItemId(itemDTO.getId());
        if (movie != null)
            return movieConverter.convert(movie);
        return null;
    }

    @Override
    @Transactional
    public void addCategoriesToMovie(MovieDTO movieDTO, List<CategoryDTO> categoryDTOS) throws ObjectNull, NameFieldNull, ObjectNotFound, ObjectAlreadyExists, IdFieldNull {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new IdFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        if (categoryDTOS == null || categoryDTOS.isEmpty())
            throw new ObjectNull(MessageConstants.LIST_CATEGORY_DTO_NULL);
        List<String> categoryNames = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOS) {
            if (categoryDTO.getName() == null)
                throw new NameFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
            categoryNames.add(categoryDTO.getName());
        }
        movieService.addCategoriesToMovie(movieDTO.getId(), categoryNames);
    }
}
