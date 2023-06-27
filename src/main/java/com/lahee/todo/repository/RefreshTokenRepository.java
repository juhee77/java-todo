package com.lahee.todo.repository;


import com.lahee.todo.dto.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);

    void deleteByKey(String nickname);
}
