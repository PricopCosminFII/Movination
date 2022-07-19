package com.movie.service.impl;

import com.movie.model.Role;
import com.movie.model.User;
import com.movie.repository.UserDAO;
import com.movie.service.RoleService;
import com.movie.service.UserService;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Setter
public class UserServiceImpl implements UserService {

    UserDAO userDAO;
    RoleService roleService;

    @Override
    @Transactional
    public void save(User user) {
        Role role = roleService.getByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        userDAO.save(user);
    }

    public boolean existsUserByEmail(User user) {
        return userDAO.existsUserByEmail(user.getEmail());
    }

}
