package com.movie.converter;

import com.movie.dto.RoleDTO;
import com.movie.model.Role;
import org.modelmapper.ModelMapper;


public class RoleConverter {
    ModelMapper modelMapper;

    public RoleDTO convert(Role role) {
        if (role != null) {
            return modelMapper.map(role, RoleDTO.class);
        }
        return null;
    }

    public Role convert(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return modelMapper.map(roleDTO, Role.class);
        }
        return null;
    }
}
