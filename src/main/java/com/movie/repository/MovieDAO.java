package com.movie.repository;

import com.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieDAO extends JpaRepository<Movie, Long>, CrudRepository<Movie, Long> {
    Movie findMovieByName(String movie);

    Movie findMovieById(Long id);

    List<Movie> findMoviesByNameContains(String s);

    @Modifying
    @Query("update Movie m set m.name = :name, m.description = :description, m.minutes = :minutes, m.picture = :picture, m.year = :year where m.id = :id")
    void update(@Param(value = "id") Long id, @Param(value = "name") String name, @Param(value = "description") String description, @Param(value = "minutes") Long minutes, @Param(value = "picture") String picture, @Param(value = "year") Integer year);
}
