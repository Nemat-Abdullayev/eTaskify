package com.etaskify.service;

import com.etaskify.dto.request.UserDTO;
import com.etaskify.dto.request.UserRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.model.User;

import java.util.Optional;

public interface UserService {

    ApiResponse<?> create(UserRequestDTO requestDTO);

    Optional<User> findUserByEmail(String email);

    Optional<User> findById(Long id);
}
