package com.movie.facade;

import com.movie.dto.UserDTO;

public interface UserFacade {
    void register(UserDTO userDTO);

    boolean existsUserByEmail(UserDTO userDTO);
}
