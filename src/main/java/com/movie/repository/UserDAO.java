package com.movie.repository;

import com.movie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    @Override
    <S extends User> S save(S entity);

    boolean existsUserByEmail(String s);

    User findUserByEmail(String email);
}
