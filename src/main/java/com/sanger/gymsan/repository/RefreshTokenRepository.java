package com.sanger.gymsan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
