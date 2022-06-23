package com.etaskify.service.impl;


import com.etaskify.dto.request.OrganizationRequestDTO;
import com.etaskify.dto.request.UserDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.dto.response.UserResponseDTO;
import com.etaskify.enums.RoleName;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.mapper.OrganizationMapper;
import com.etaskify.mapper.UserMapper;
import com.etaskify.model.Organization;
import com.etaskify.model.User;
import com.etaskify.repository.OrganizationRepository;
import com.etaskify.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public ApiResponse<?> create(OrganizationRequestDTO requestDTO) {
        log.info("organization save processing by requested data {}", requestDTO);
        Organization organization = OrganizationMapper.INSTANCE.mapDtoToEntity(requestDTO);
        organization.getUsers().forEach(user -> user.setOrganization(organization));
        organizationRepository.save(organization);
        return new ApiResponse<>(true);
    }

    @Override
    public ApiResponse<?> getUsersOfOrganization(Long id) {
        log.info("get users of organization by organization id {}", id);

        Organization organization = organizationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Organization.class, "id", String.valueOf(id)));
        List<UserResponseDTO> responseDTOS = organization.getUsers()
                .stream()
                .filter(user -> user.getRole().getName().equals(RoleName.USER))
                .map(UserMapper.INSTANCE::maoEntityToDto).toList();

        return new ApiResponse<>(true, responseDTOS);
    }
}
