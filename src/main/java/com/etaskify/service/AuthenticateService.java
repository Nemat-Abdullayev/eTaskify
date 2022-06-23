package com.etaskify.service;

import com.etaskify.dto.request.AuthRequest;
import com.etaskify.dto.request.RefreshTokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {

    ResponseEntity<?> generateToken(AuthRequest authRequest)
            throws Exception;

    ResponseEntity<?> refreshToken(RefreshTokenDTO refreshTokenDTO);
}
