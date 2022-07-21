package com.movie.facade.impl;

import com.movie.converter.UserConverter;
import com.movie.dto.UserDTO;
import com.movie.facade.UserFacade;
import com.movie.model.User;
import com.movie.service.UserService;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Setter
@Transactional
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

    @Override
    public String validateUser(UserDTO userDTO) {
        boolean isUserRegistered = userService.existsUserByEmail(userConverter.convert(userDTO));
        boolean isPasswordCorrect = userDTO.getPassword().equals(userDTO.getConfirmPassword());
        String error = null;
        if (isUserRegistered) {
            error = "There is a user with that email!";
        } else if (!isPasswordCorrect) {
            error = "The passwords do not match!";
        }
        return error;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user != null)
            return userConverter.convert(user);
        return null;
    }
}
