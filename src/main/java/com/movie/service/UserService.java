package com.movie.service;

import com.movie.model.User;

public interface UserService {

    void save(User user);

    boolean existsUserByEmail(User user);

    User getUserByEmail(String email);
}
