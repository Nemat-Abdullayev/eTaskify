package com.etaskify.service.impl;

import com.etaskify.repository.OrganizationRepository;
import com.etaskify.repository.TaskRepository;
import com.etaskify.repository.UserRepository;
import com.etaskify.util.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

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
    void getTasksForUsers() {
    }
}