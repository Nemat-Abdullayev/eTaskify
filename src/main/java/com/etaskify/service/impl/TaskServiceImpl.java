package com.etaskify.service.impl;

import com.etaskify.dto.request.TaskRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.dto.response.TaskResponseDTO;
import com.etaskify.enums.Status;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.mapper.TaskMapper;
import com.etaskify.model.Organization;
import com.etaskify.model.Task;
import com.etaskify.model.User;
import com.etaskify.repository.OrganizationRepository;
import com.etaskify.repository.TaskProjection;
import com.etaskify.repository.TaskRepository;
import com.etaskify.repository.UserRepository;
import com.etaskify.service.TaskService;
import com.etaskify.util.AuthUtil;
import com.etaskify.util.Parser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Parser parserService;
    private final TaskRepository taskRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ApiResponse<?> create(TaskRequestDTO taskRequestDTO) {
        log.info("create task with default status");
        Task task = TaskMapper.INSTANCE.mapDto(taskRequestDTO);

        task.setStatus(Status.CREATED);
        task.setEndDate(parserService.calculateEnDateOfTask(LocalDateTime.now(), taskRequestDTO.getDeadLine()));

        Long organizationId = taskRequestDTO.getOrganizationId();
        Organization createdOrganization = organizationRepository.findById(taskRequestDTO.getOrganizationId())
                .orElseThrow(
                        () -> new EntityNotFoundException(Organization.class, "id", String.valueOf(organizationId)));

        task.setOrganization(createdOrganization);
        taskRepository.save(task);

        return new ApiResponse<>(true);
    }

    @Override
    public ApiResponse<?> getTasksForManager() {
        log.info("find all task by logged user if role is manager");
        String username = AuthUtil.getUsername();
        User user = userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(User.class, "username", username)
                );

        Organization organization = user.getOrganization();
        List<TaskResponseDTO> taskResponseDTOS = organization.getTasks()
                .stream()
                .map(TaskMapper.INSTANCE::mapEntityToDto)
                .toList();

        return new ApiResponse<>(true, taskResponseDTOS);
    }

    @Override
    public ApiResponse<?> getTasksForUsers() {
        log.info("get tasks of user which is assigned to");
        List<TaskProjection> taskProjections = taskRepository.findAllTaskOfUser(AuthUtil.getUsername());
        List<TaskResponseDTO> taskResponseDTOS = taskProjections.stream()
                .map(TaskMapper.INSTANCE::mapProjectionToDto)
                .toList();
        return new ApiResponse<>(true, taskResponseDTOS);
    }
}
