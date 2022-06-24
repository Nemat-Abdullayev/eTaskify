package com.etaskify.service.impl;

import com.etaskify.dto.request.TaskRequestDTO;
import com.etaskify.dto.response.ApiResponse;
import com.etaskify.dto.response.TaskResponseDTO;
import com.etaskify.exception.custom.EntityNotFoundException;
import com.etaskify.mapper.TaskMapper;
import com.etaskify.model.Organization;
import com.etaskify.model.Task;
import com.etaskify.model.User;
import com.etaskify.repository.OrganizationRepository;
import com.etaskify.repository.TaskProjection;
import com.etaskify.repository.TaskRepository;
import com.etaskify.repository.UserRepository;
import com.etaskify.util.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

class TaskServiceImplTest {


    @Mock
    private Parser parserService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private UserRepository userRepository;

    private TaskServiceImpl taskServiceUnderTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskServiceUnderTest = new TaskServiceImpl(parserService, taskRepository, organizationRepository, userRepository);

    }

    @Test
    void getTasksForUsersTest() {
        //given
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

        TaskProjection taskProjectionFirst = factory.createProjection(TaskProjection.class);
        taskProjectionFirst.setTitle("TitleFirst");

        TaskProjection taskProjectionSecond = factory.createProjection(TaskProjection.class);
        taskProjectionSecond.setTitle("TitleSecond");

        TaskProjection taskProjectionThird = factory.createProjection(TaskProjection.class);
        taskProjectionThird.setTitle("TitleThird");

        given(taskRepository.findAllTaskOfUser("azer@gmail.com")).willReturn(
                List.of(taskProjectionFirst, taskProjectionSecond, taskProjectionThird)
        );

        //when
        ApiResponse<?> tasksForUsersResponse = taskServiceUnderTest.getTasksForUsers();


        //then
        assertTrue(tasksForUsersResponse.isSuccess());
        assertThat(tasksForUsersResponse.getData()).isNotNull();

    }

    @Test
    void getTasksForManagerTestUserIsExist() {

        //given

        String email = "azer@gmail.com";

        Task taskMock = Task.builder()
                .deadLine("2 hours")
                .endDate(LocalDateTime.now())
                .build();

        Organization organizationMock = Organization.builder()
                .name("test org")
                .tasks(List.of(taskMock))
                .build();

        User userMock = User.builder()
                .name("Azer")
                .organization(organizationMock)
                .email(email)
                .build();

        given(userRepository.findByEmail(null)).willReturn(Optional.of(userMock));

        //when

        ApiResponse<?> tasksForManagerResponse = taskServiceUnderTest.getTasksForManager();

        //then
        assertTrue(tasksForManagerResponse.isSuccess());

    }

    @Test
    void getTasksForManagerTestUserNotExist() {

        //given

        String email = "azer@gmail.com";

        Task taskMock = Task.builder()
                .deadLine("2 hours")
                .endDate(LocalDateTime.now())
                .build();

        Organization organizationMock = Organization.builder()
                .name("test org")
                .tasks(List.of(taskMock))
                .build();

        User userMock = User.builder()
                .name("Azer")
                .organization(organizationMock)
                .email(email)
                .build();

        given(userRepository.findByEmail(email)).willReturn(Optional.of(userMock));

        //when


        //then
        assertThatThrownBy(
                () -> taskServiceUnderTest.getTasksForManager())
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User was not found for parameters [username, " + null + "]");

    }


    @Test
    void getTasksForManagerTestMapToTaskResponseDto() {

        //given

        String email = "azer@gmail.com";

        Task taskMock = Task.builder()
                .deadLine("2 hours")
                .endDate(LocalDateTime.now())
                .build();

        List<Task> mockTaskList = List.of(taskMock);
        Organization organizationMock = Organization.builder()
                .name("test org")
                .tasks(mockTaskList)
                .build();

        User userMock = User.builder()
                .name("Azer")
                .organization(organizationMock)
                .email(email)
                .build();

        given(userRepository.findByEmail(null)).willReturn(Optional.of(userMock));

        //when
        ApiResponse<?> tasksForManagerResponse = taskServiceUnderTest.getTasksForManager();
        List<TaskResponseDTO> taskResponseDTOS = (List<TaskResponseDTO>) tasksForManagerResponse.getData();
        assertThat(taskResponseDTOS.size()).isEqualTo(mockTaskList.size());


        //then;

    }

    @Test
    void createTaskTest() {

        //givem
        TaskRequestDTO mockTaskRequestDTO = TaskRequestDTO.builder()
                .deadLine("2 hours")
                .description("this is test desc.")
                .organizationId(2L)
                .title("test title")
                .build();

        Organization mockOrganization = Organization.builder()
                .name("tech")
                .build();
        mockOrganization.setId(2L);

        given(organizationRepository.findById(2L)).willReturn(Optional.of(mockOrganization));

        //when
        ApiResponse<?> createTaskResponse = taskServiceUnderTest.create(mockTaskRequestDTO);

        assertTrue(createTaskResponse.isSuccess());

    }

    @Test
    void createTaskTestOrganizationNotFoundByRequestedId() {

        //givem
        TaskRequestDTO mockTaskRequestDTO = TaskRequestDTO.builder()
                .deadLine("2 hours")
                .description("this is test desc.")
                .organizationId(2L)
                .title("test title")
                .build();

        Organization mockOrganization = Organization.builder()
                .name("tech")
                .build();
        mockOrganization.setId(2L);

        given(organizationRepository.findById(3L)).willReturn(Optional.of(mockOrganization));

        //then
        assertThatThrownBy(
                () -> taskServiceUnderTest.create(mockTaskRequestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Organization was not found for parameters [id, " + 2 + "]");


    }
}