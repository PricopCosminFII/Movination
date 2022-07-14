package com.movie.facade;

import com.movie.dto.MovieDTO;

import java.util.List;

public interface MovieFacade {

    List<MovieDTO> getAll();
}
