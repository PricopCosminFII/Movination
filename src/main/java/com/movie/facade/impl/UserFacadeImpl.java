package com.movie.facade.impl;

import com.movie.converter.UserConverter;
import com.movie.dto.UserDTO;
import com.movie.facade.UserFacade;
import com.movie.service.UserService;
import lombok.Setter;

@Setter
public class UserFacadeImpl implements UserFacade {

    UserConverter userConverter;
    UserService userService;

    @Override
    public void register(UserDTO userDTO) {
        userService.save(userConverter.convert(userDTO));
    }

    @Override
    public boolean existsUserByEmail(UserDTO userDTO) {
        return userService.existsUserByEmail(userConverter.convert(userDTO));
    }
}
