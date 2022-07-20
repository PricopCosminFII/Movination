package com.movie.service.impl;

import com.movie.model.Role;
import com.movie.model.User;
import com.movie.repository.UserDAO;
import com.movie.service.RoleService;
import com.movie.service.UserService;
import lombok.Setter;

import java.util.Collections;

@Setter
public class UserServiceImpl implements UserService {

    UserDAO userDAO;
    RoleService roleService;

    @Override
    public void save(User user) {
        Role role = roleService.getByName("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleService.save(role);
        }
        user.setRoles(Collections.singletonList(role));
        userDAO.save(user);
    }

    @Override
    public boolean existsUserByEmail(User user) {
        return userDAO.existsUserByEmail(user.getEmail());
    }

}
