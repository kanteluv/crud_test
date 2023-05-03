package com.sparta.crudtest.repository;

import com.sparta.crudtest.entity.RefreshToken;
import io.jsonwebtoken.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);
}
