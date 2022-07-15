package com.movie.service.impl;

import com.movie.constants.MessageConstants;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Category;
import com.movie.model.Movie;
import com.movie.repository.CategoryDAO;
import com.movie.repository.MovieDAO;
import com.movie.service.CategoryService;
import lombok.Setter;

import java.util.List;

@Setter
public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;

    @Override
    public void save(Category category) throws ObjectAlreadyExists {
        if (categoryDAO.findCategoryByName(category.getName()) != null)
            throw new ObjectAlreadyExists(MessageConstants.CATEGORY_EXISTS);
        categoryDAO.save(category);
    }

    @Override
    public List<Category> getCategoriesByMovieName(Long idMovie) throws ObjectNotFound {
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null)
            throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        return movie.getCategories();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }
}
