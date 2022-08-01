package com.movie.facade.impl;

import com.movie.constants.MessageConstants;
import com.movie.converter.MovieConverter;
import com.movie.dto.CategoryDTO;
import com.movie.dto.MovieDTO;
import com.movie.dto.WatchlistItemDTO;
import com.movie.exception.*;
import com.movie.facade.CategoryFacade;
import com.movie.facade.MovieFacade;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter
@Transactional
public class MovieFacadeImpl implements MovieFacade {
    private MovieService movieService;
    private MovieConverter movieConverter;
    private CategoryFacade categoryFacade;

    @Override
    public void save(MovieDTO movieDTO) throws ObjectNull, RequiredFieldNull, ObjectAlreadyExists, InvalidData {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getName() == null)
            throw new RequiredFieldNull(MessageConstants.NAME_FIELD_OF_MOVIE_DTO_NULL);
        movieService.save(movieConverter.convert(movieDTO));

    }

    @Override
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
    public List<MovieDTO> getMoviesByCategory(CategoryDTO categoryDTO) throws ObjectNull, RequiredFieldNull, ObjectNotFound {
        if (categoryDTO == null)
            throw new ObjectNull(MessageConstants.CATEGORY_DTO_NULL);
        if (categoryDTO.getName() == null)
            throw new RequiredFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
        List<Movie> movies = movieService.getMovieByCategoryName(categoryDTO.getName());
        if (movies != null && !movies.isEmpty())
            return getMovieDTOS(movies);
        return null;
    }

    @Override
    public MovieDTO getMovieByItem(WatchlistItemDTO watchlistItemDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        if (watchlistItemDTO == null)
            throw new ObjectNull(MessageConstants.ITEM_DTO_NULL);
        if (watchlistItemDTO.getId() == null)
            throw new RequiredFieldNull(MessageConstants.ID_FIELD_OF_ITEM_DTO_NULL);
        Movie movie = movieService.getMovieByItemId(watchlistItemDTO.getId());
        if (movie != null)
            return movieConverter.convert(movie);
        return null;
    }

    @Override
    public void addCategoriesToMovie(MovieDTO movieDTO, List<CategoryDTO> categoryDTOS) throws ObjectNull, RequiredFieldNull, ObjectNotFound, ObjectAlreadyExists {
        if (movieDTO == null)
            throw new ObjectNull(MessageConstants.MOVIE_DTO_NULL);
        if (movieDTO.getId() == null)
            throw new RequiredFieldNull(MessageConstants.ID_FIELD_OF_MOVIE_DTO_NULL);
        if (categoryDTOS == null || categoryDTOS.isEmpty())
            throw new ObjectNull(MessageConstants.LIST_CATEGORY_DTO_NULL);
        List<String> categoryNames = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOS) {
            if (categoryDTO.getName() == null)
                throw new RequiredFieldNull(MessageConstants.NAME_FIELD_OF_CATEGORY_DTO_NULL);
            categoryNames.add(categoryDTO.getName());
        }
        movieService.addCategoriesToMovie(movieDTO.getId(), categoryNames);
    }

    @Override
    public MovieDTO getById(Long id) {
        return movieConverter.convert(movieService.getById(id));
    }

    @Override
    public List<MovieDTO> getMovieBySearch(String search) throws ObjectNotFound {
        List<Movie> movies = movieService.getMovieBySearch(search);
        if (movies.isEmpty() || search.isEmpty()) {
            throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        }
        return getMovieDTOS(movies);
    }

    @Override
    public void deleteMovie(MovieDTO movieDTO) throws ObjectNotFound {
        if (movieDTO.getId() != null)
            movieService.delete(movieDTO.getId());
    }

    @Override
    public List<MovieDTO> getMovieRecommendation(List<CategoryDTO> categoryDTOList, MovieDTO movieDTO) throws ObjectNull, ObjectNotFound, RequiredFieldNull {
        List<MovieDTO> moviesFromAllCategory = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOList) {
            List<MovieDTO> moviesFromCategory = getMoviesByCategory(categoryDTO);
            moviesFromAllCategory.addAll(moviesFromCategory);
        }
        List<MovieDTO> movieRecommendation = moviesFromAllCategory.stream().distinct().collect(Collectors.toList());
        movieRecommendation.remove(movieDTO);
        movieRecommendation.removeIf(movieWithoutRating -> movieWithoutRating.getRating() == null);
        movieRecommendation.sort(Comparator.comparing(MovieDTO::getRating).reversed());
        return movieRecommendation;
    }

    @Override
    public String validateMovie(MovieDTO movieDTO) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String error = null;
        if (movieDTO.getMinutes() < 0) {
            error = "Movie duration should be non-negative!";
        } else if (movieDTO.getYear() < 1878 || movieDTO.getYear() > year) {
            error = "Movie's release year should be between 1878 and current year!";
        }
        return error;
    }

    @Override
    public void update(Long id, String name, String description, Long minutes, String picture, Integer year) throws InvalidData, ObjectNotFound, ObjectNull {
        movieService.update(id, name, description, minutes, picture, year);
    }

    @Override
    public void deleteCategories(MovieDTO movieDTO, List<CategoryDTO> categoriesDTO) {
        Movie movie = movieService.getById(movieDTO.getId());
        movieService.deleteCategories(movie, movie.getCategories());
    }

    @SneakyThrows
    @Override
    public List<CategoryDTO> setCategoriesToMovie(List<String> categories) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (String category : categories) {
            if (categoryFacade.getCategoryByName(category) != null) {
                categoryDTOS.add(categoryFacade.getCategoryByName(category));
            } else {
                categoryFacade.save(new CategoryDTO(category));
                CategoryDTO newCategory = categoryFacade.getCategoryByName(category);
                categoryDTOS.add(newCategory);
            }
        }
        return categoryDTOS;
    }
}
