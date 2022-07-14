package com.movie.repository;

import com.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, Long> {
    List<Movie> findAll();
}
