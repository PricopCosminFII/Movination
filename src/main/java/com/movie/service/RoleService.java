package com.movie.service;

import com.movie.model.Role;

public interface RoleService {
    Role getByName(String s);
    void save(Role role);
}
