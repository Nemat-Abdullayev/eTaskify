package com.etaskify.service.impl;

import com.etaskify.dto.request.TaskAssignRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.enums.Notification;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.model.Organization;
import com.etaskify.model.Task;
import com.etaskify.model.TaskAssign;
import com.etaskify.model.User;
import com.etaskify.repository.TaskAssignRepository;
import com.etaskify.repository.TaskRepository;
import com.etaskify.service.EmailService;
import com.etaskify.service.TaskAssignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAssignServiceImpl implements TaskAssignService {

    private final TaskAssignRepository taskAssignRepository;
    private final TaskRepository taskRepository;

    private final EmailService emailService;

    private static final String MESSAGE = "Task assigned to , task id is : ";


    @Override
    @Transactional
    public ApiResponse<?> assignTask(TaskAssignRequestDTO taskAssignRequestDTO) {
        log.info("assign task by requested data {}", taskAssignRequestDTO);


        Task task = taskRepository.findById(taskAssignRequestDTO.getTaskId())
                .orElseThrow(
                        () -> new EntityNotFoundException(Task.class, "id", String.valueOf(taskAssignRequestDTO.getTaskId()))
                );

        Organization organization = task.getOrganization();
        List<User> users = new ArrayList<>();
        taskAssignRequestDTO.getUserIdList().forEach(userId -> {
            Optional<User> optionalUser = organization.getUsers()
                    .stream()
                    .filter(user -> user.getId().equals(userId))
                    .findFirst();
            optionalUser.ifPresent(users::add);
        });

        TaskAssign taskAssign = TaskAssign.builder()
                .task(task)
                .organization(organization)
                .users(users)
                .build();

        taskAssignRepository.save(taskAssign);
        log.info("task assigned to user and sending notification to mail");
        users.forEach(user -> {
            emailService.sendNotification(user.getEmail(), Notification.TASK_ASSIGNED.name(), MESSAGE + task.getId());
        });
        return new ApiResponse<>(true);
    }
}
