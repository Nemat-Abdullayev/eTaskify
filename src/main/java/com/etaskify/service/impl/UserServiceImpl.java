package com.etaskify.service.impl;

import com.etaskify.dto.request.UserRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.mapper.UserMapper;
import com.etaskify.model.Organization;
import com.etaskify.model.Role;
import com.etaskify.model.User;
import com.etaskify.repository.OrganizationRepository;
import com.etaskify.repository.RoleRepository;
import com.etaskify.repository.UserRepository;
import com.etaskify.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final String password;
    private final PasswordEncoder passwordEncoder;

    private final OrganizationRepository organizationRepository;

    public UserServiceImpl(UserRepository userRepository,
                           @Value("${app.default.user.password}") String password,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.password = password;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ApiResponse<?> create(UserRequestDTO userDTO) {
        log.info("create user by requested data {}", userDTO);
        User user = UserMapper.INSTANCE.mapDtoToEntity(userDTO);
        Organization organization = organizationRepository.findById(userDTO.getOrganizationId())
                .orElseThrow(
                        () -> new EntityNotFoundException(Organization.class, "id", String.valueOf(userDTO.getOrganizationId()))
                );
        Role role = roleRepository.findByNameAndActiveTrue(userDTO.getRoleName()).orElseThrow(
                () -> new EntityNotFoundException(Role.class, "role name", userDTO.getRoleName().name())
        );
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setOrganization(organization);
        userRepository.save(user);
        return new ApiResponse<>(true);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        log.info("find user by username");
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("find user by user id if exists ");
        return userRepository.findById(id);
    }
}
