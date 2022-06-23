package com.etaskify.service;

import com.etaskify.dto.request.TaskRequestDTO;
import com.etaskify.dto.response.ApiResponse;

public interface TaskService {

    ApiResponse<?> create(TaskRequestDTO taskRequestDTO);

    ApiResponse<?> getTasksForManager();

    ApiResponse<?> getTasksForUsers();
}
