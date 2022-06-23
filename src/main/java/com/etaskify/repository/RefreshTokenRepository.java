package com.etaskify.repository;
import com.etaskify.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenAndActiveTrue(String refreshToken);

    Optional<RefreshToken> findByUserIdAndActiveTrue(Long userId);
}
