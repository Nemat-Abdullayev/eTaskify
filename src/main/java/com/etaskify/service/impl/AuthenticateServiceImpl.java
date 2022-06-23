package com.etaskify.service.impl;
import com.etaskify.dto.request.AuthRequest;
import com.etaskify.dto.request.RefreshTokenDTO;
import com.etaskify.dto.response.AuthResponseDTO;
import com.etaskify.exception.custom.AuthenticationException;
import com.etaskify.exception.custom.InvalidRefreshTokenException;
import com.etaskify.exception.custom.RefreshTokenExpiredException;
import com.etaskify.model.RefreshToken;
import com.etaskify.model.User;
import com.etaskify.security.JwtTokenUtil;
import com.etaskify.service.AuthenticateService;
import com.etaskify.service.RefreshTokenService;
import com.etaskify.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;


    @Override
    @SneakyThrows
    public ResponseEntity<?> generateToken(AuthRequest authRequest) {
        log.info("Enter inputs of authentication request {}", authRequest);
        String email = authRequest.getEmail();
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        final User user = userService.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("user not found by this email [ " + email + " ]"));
        log.debug("generating token");
        final String accessToken = jwtTokenUtil.generateAccessToken(user);


        final String refreshToken = jwtTokenUtil.generateRefreshToken();
        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findOneByUserId(user.getId());
        RefreshToken refreshTokenEntity = RefreshToken.builder().build();
        if (optionalRefreshToken.isPresent()) {
            refreshTokenEntity = optionalRefreshToken.get();
            log.info("refresh token is exist, operation is update");
            modifyRefreshTokenEntity(user, accessToken, refreshToken, refreshTokenEntity);
        } else {
            log.info("refresh token not exist,operation is insert");
            modifyRefreshTokenEntity(user, accessToken, refreshToken, refreshTokenEntity);
        }
        refreshTokenService.saveOrUpdate(refreshTokenEntity);

        return ResponseEntity.ok(
                AuthResponseDTO.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build());
    }

    private void modifyRefreshTokenEntity(User user, String accessToken,
                                          String refreshToken,
                                          RefreshToken refreshTokenEntity) {
        refreshTokenEntity.setActive(true);
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenEntity.setRefreshToken(refreshToken);
        refreshTokenEntity.setAccessToken(accessToken);
        refreshTokenEntity.setExpiredDateTime(LocalDateTime.now().plusHours(4));
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenDTO refreshTokenDTO) {
        log.info("get access token by refresh token if exist and valid");
        RefreshToken refreshTokenEntity = refreshTokenService.findOne(
                refreshTokenDTO.getRefreshToken()).orElseThrow(
                () -> new InvalidRefreshTokenException("refresh token is invalid"));
        if (Objects.nonNull(refreshTokenEntity)) {
            if (refreshTokenEntity.getExpiredDateTime().isBefore(LocalDateTime.now())) {
                log.error(String.format("refresh token expired in %s time",
                        refreshTokenEntity.getExpiredDateTime()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:ss"))));
                throw new RefreshTokenExpiredException(String.format("refresh token expired in %s time",
                        refreshTokenEntity.getExpiredDateTime()
                                .format(DateTimeFormatter.ofPattern("dd.MM.yyy HH:ss"))));
            }
            Long userID = refreshTokenEntity.getUserId();
            User user = userService.findById(userID).orElseThrow(
                    () -> new UsernameNotFoundException(String.format("user not found with %s this id", userID)));
            final String accessToken = jwtTokenUtil.generateAccessToken(user);
            final String refreshToken = jwtTokenUtil.generateRefreshToken();
            refreshTokenEntity.setAccessToken(accessToken);
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenEntity.setExpiredDateTime(LocalDateTime.now().plusHours(4));
            refreshTokenService.saveOrUpdate(refreshTokenEntity);
            return new ResponseEntity<>(
                    AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build(),
                    HttpStatus.OK);
        }
        throw new InvalidRefreshTokenException("refresh token is null");
    }

    private void authenticate(String username, String password) {
        if (Objects.nonNull(password) && Objects.nonNull(username)) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (DisabledException e) {
                log.error("USER_DISABLED", e);
                throw new AuthenticationException("USER_DISABLED");
            } catch (BadCredentialsException e) {
                log.error("invalid credentials entered - {}", e.getClass());
                throw new AuthenticationException("INVALID_CREDENTIALS");
            }
        }

    }
}