package com.etaskify.service.impl;

import com.etaskify.dto.request.TaskAssignRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.enums.RoleName;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.model.Organization;
import com.etaskify.model.Role;
import com.etaskify.model.Task;
import com.etaskify.model.User;
import com.etaskify.repository.TaskAssignRepository;
import com.etaskify.repository.TaskRepository;
import com.etaskify.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class TaskAssignServiceImplTest {

    @Mock
    private TaskAssignRepository taskAssignRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private EmailService emailService;

    private TaskAssignServiceImpl taskAssignServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskAssignServiceUnderTest = new TaskAssignServiceImpl(taskAssignRepository, taskRepository, emailService);
    }

    @Test
    void assignTaskTest() {

        Role mockRole = Role.builder()
                .active(true)
                .name(RoleName.USER)
                .build();

        User mockUser = User.builder()
                .role(mockRole)
                .name("azer")
                .password("skjdfh")
                .build();

        mockUser.setId(1L);

        TaskAssignRequestDTO mockTaskAssignRequestDTO = TaskAssignRequestDTO.builder()
                .taskId(2L)
                .userIdList(List.of(1L))
                .build();


        Organization mockOrganization = Organization.builder()
                .name("tech")
                .address("bla bla")
                .phoneNumber("3847239423")
                .users(List.of(mockUser))
                .build();

        Task mockTask = Task.builder()
                .description("testTitle")
                .organization(mockOrganization)
                .endDate(LocalDateTime.now().plusDays(2))
                .deadLine("2 days")
                .build();
        mockTask.setId(2L);

        given(taskRepository.findById(2L)).willReturn(Optional.of(mockTask));
        ApiResponse<?> responseFromTaskAssign = taskAssignServiceUnderTest.assignTask(mockTaskAssignRequestDTO);
        assertTrue(responseFromTaskAssign.isSuccess());
    }

    @Test
    void assignTaskTaskNotFound() {

        Role mockRole = Role.builder()
                .active(true)
                .name(RoleName.USER)
                .build();

        User mockUser = User.builder()
                .role(mockRole)
                .name("azer")
                .password("skjdfh")
                .build();

        mockUser.setId(1L);

        TaskAssignRequestDTO mockTaskAssignRequestDTO = TaskAssignRequestDTO.builder()
                .taskId(2L)
                .userIdList(List.of(1L))
                .build();


        Organization mockOrganization = Organization.builder()
                .name("tech")
                .address("bla bla")
                .phoneNumber("3847239423")
                .users(List.of(mockUser))
                .build();

        Task mockTask = Task.builder()
                .description("testTitle")
                .organization(mockOrganization)
                .endDate(LocalDateTime.now().plusDays(2))
                .deadLine("2 days")
                .build();
        mockTask.setId(2L);

        given(taskRepository.findById(3L)).willReturn(Optional.of(mockTask));

        assertThatThrownBy(
                () -> taskAssignServiceUnderTest.assignTask(mockTaskAssignRequestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Task was not found for parameters [id, " + 2 + "]");


    }
}