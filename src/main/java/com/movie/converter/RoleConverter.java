package com.movie.converter;

import com.movie.dto.RoleDTO;
import com.movie.model.Role;
import org.modelmapper.ModelMapper;


public class RoleConverter {
    private final ModelMapper modelMapper = new ModelMapper();

    public RoleDTO convert(Role role) {
        if(role != null) {
             RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
             return roleDTO;
        }
        return null;
    }

    public Role convert(RoleDTO roleDTO) {
        if(roleDTO == null) {
            Role role = modelMapper.map(roleDTO, Role.class);
            return role;
        }
        return null;
    }
}
