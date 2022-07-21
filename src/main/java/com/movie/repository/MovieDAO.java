package com.movie.repository;

import com.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Long>, CrudRepository<Movie, Long> {
    Movie findMovieByName(String movie);

    Movie findMovieById(Long id);

    List<Movie> findMoviesByNameContains(String s);
}
