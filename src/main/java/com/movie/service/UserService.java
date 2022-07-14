package com.movie.service;

import com.movie.model.User;

public interface UserService {

    void save(User user);
    public boolean existsUserByEmail(User user);
}
