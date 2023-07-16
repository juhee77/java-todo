package com.lahee.todo.repository;

import com.lahee.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByUsername(String name);

    Optional<User> findOneWithAuthoritiesByUsername(String username); //TODO
}
