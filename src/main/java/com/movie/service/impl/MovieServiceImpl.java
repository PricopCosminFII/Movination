package com.movie.service.impl;

import com.movie.constants.MessageConstants;
import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Category;
import com.movie.model.Item;
import com.movie.model.Movie;
import com.movie.repository.CategoryDAO;
import com.movie.repository.ItemDAO;
import com.movie.repository.MovieDAO;
import com.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Setter
public class MovieServiceImpl implements MovieService {
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;
    private ItemDAO itemDAO;

    @Override
    public void save(Movie movie) throws ObjectAlreadyExists, InvalidData {
        if (movieDAO.findMovieById(movie.getId()) != null)
            throw new ObjectAlreadyExists(MessageConstants.MOVIE_EXISTS);
        if (movie.getYear() != null && (movie.getYear() < 1800 || movie.getYear() > getCurrentYear()))
            throw new InvalidData(MessageConstants.INVALID_YEAR);
        if (movie.getMinutes() != null && movie.getMinutes() < 1)
            throw new InvalidData(MessageConstants.INVALID_DURATION);
        movieDAO.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.findAll();
    }

    private Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public Movie getById(Long id) {
        return movieDAO.findMovieById(id);
    }

    @Override
    public List<Movie> getMovieByCategoryName(String categoryName) throws ObjectNotFound {
        Category category = categoryDAO.findCategoryByName(categoryName);
        if (category == null)
            throw new ObjectNotFound(MessageConstants.CATEGORY_NOT_FOUND);
        return category.getMovies();
    }

    @Override
    public void addCategoriesToMovie(Long idMovie, List<String> categoryNames) throws ObjectNotFound, ObjectAlreadyExists {
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null)
            throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        List<Category> categories = new ArrayList<>();
        for (String categoryName : categoryNames) {
            Category category = categoryDAO.findCategoryByName(categoryName);
            if (category == null)
                throw new ObjectNotFound(MessageConstants.CATEGORY_NOT_FOUND);
            if (movie.getCategories().contains(category))
                throw new ObjectAlreadyExists(MessageConstants.CATEGORY_EXISTS_IN_MOVIE);
            categories.add(category);
        }
        movie.setCategories(categories);
        movieDAO.save(movie);
        addMovieToCategories(movie, categories);
    }

    @Override
    public Movie getMovieByItemId(Long id) throws ObjectNotFound {
        Item item = itemDAO.findItemById(id);
        if (item == null)
            throw new ObjectNotFound(MessageConstants.ITEM_NOT_FOUND);
        return item.getMovie();
    }

    private void addMovieToCategories(Movie movie, List<Category> categories) {
        if (categories.size() > 0 && movie != null) {
            for (Category category : categories) {
                List<Movie> moviesOfCategory = category.getMovies();
                moviesOfCategory.add(movie);
                category.setMovies(moviesOfCategory);
                categoryDAO.save(category);
            }
        }
    }
}
