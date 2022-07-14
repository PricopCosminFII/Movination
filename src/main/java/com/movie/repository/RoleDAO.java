package com.movie.repository;

import com.movie.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {

    Role findByName(String s);
}
