package com.etaskify.service;

import com.etaskify.dto.request.TaskAssignRequestDTO;
import com.etaskify.dto.response.ApiResponse;

public interface TaskAssignService {
    ApiResponse<?> assignTask(TaskAssignRequestDTO taskAssignRequestDTO);

}
