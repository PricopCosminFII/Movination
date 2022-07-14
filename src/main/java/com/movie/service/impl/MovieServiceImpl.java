package com.movie.service.impl;

import com.movie.model.Movie;
import com.movie.repository.MovieDAO;
import com.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Setter
public class MovieServiceImpl implements MovieService {
    MovieDAO movieDAO;

    @Override
    public List<Movie> getAll() {
        return movieDAO.findAll();
    }
}
