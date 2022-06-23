package com.etaskify.service;

import com.etaskify.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    void saveOrUpdate(RefreshToken refreshToken);

    Optional<RefreshToken> findOne(String refreshToken);

    Optional<RefreshToken> findOneByUserId(Long userId);
}
