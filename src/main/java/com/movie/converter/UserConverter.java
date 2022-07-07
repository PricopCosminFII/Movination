package com.movie.converter;

import com.movie.dto.UserDTO;
import com.movie.model.User;
import org.modelmapper.ModelMapper;


public class UserConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public UserDTO convert(User user) {
        if(user != null) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;
        }
        return null;
    }

    public User convert(UserDTO userDTO) {
        if(userDTO != null) {
            User user = modelMapper.map(userDTO, User.class);
            return user;
        }
        return null;
    }
}