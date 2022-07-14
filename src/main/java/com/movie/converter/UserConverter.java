package com.movie.converter;

import com.movie.dto.UserDTO;
import com.movie.model.User;
import org.modelmapper.ModelMapper;


public class UserConverter {
    ModelMapper modelMapper;

    public UserDTO convert(User user) {
        if(user != null) {
            return modelMapper.map(user, UserDTO.class);
        }
        return null;
    }

    public User convert(UserDTO userDTO) {
        if(userDTO != null) {
            return modelMapper.map(userDTO, User.class);
        }
        return null;
    }
}