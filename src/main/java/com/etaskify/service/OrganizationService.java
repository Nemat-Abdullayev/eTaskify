package com.etaskify.service;

import com.etaskify.dto.request.OrganizationRequestDTO;
import com.etaskify.dto.response.ApiResponse;

public interface OrganizationService {
    ApiResponse<?> create(OrganizationRequestDTO requestDTO);

    ApiResponse<?> getUsersOfOrganization(Long id);
}
