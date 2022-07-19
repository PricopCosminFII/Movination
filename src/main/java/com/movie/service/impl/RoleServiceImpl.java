package com.movie.service.impl;

import com.movie.model.Role;
import com.movie.repository.RoleDAO;
import com.movie.service.RoleService;
import lombok.Setter;

@Setter
public class RoleServiceImpl implements RoleService {

    RoleDAO roleDAO;

    @Override
    public Role getByName(String s) {
        return roleDAO.findByName(s);
    }
}
