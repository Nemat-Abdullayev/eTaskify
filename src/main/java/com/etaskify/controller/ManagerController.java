package com.etaskify.controller;


import com.etaskify.dto.request.TaskAssignRequestDTO;
import com.etaskify.dto.request.TaskRequestDTO;
import com.etaskify.service.OrganizationService;
import com.etaskify.service.TaskAssignService;
import com.etaskify.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_MANAGER')")
@Api(tags = "endpoint for manager")
@RequestMapping("${app.root.url}/manager")
public class ManagerController {

    private final TaskService taskService;
    private final TaskAssignService taskAssignService;

    private final OrganizationService organizationService;

    @GetMapping("/organization/{id}")
    @ApiOperation("get user list by organization id")
    public ResponseEntity<?> getUsersByOrganizationId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(organizationService.getUsersOfOrganization(id), HttpStatus.OK);
    }

    @GetMapping("/organization/task")
    @ApiOperation("get task list by logged user")
    public ResponseEntity<?> getTasks() {
        return new ResponseEntity<>(taskService.getTasksForManager(), HttpStatus.OK);
    }

    @PostMapping("/task")
    @ApiOperation("create task if role is manager")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return new ResponseEntity<>(taskService.create(taskRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/task/assign")
    @ApiOperation("assign task to organization's users")
    public ResponseEntity<?> assignTask(@Valid @RequestBody TaskAssignRequestDTO taskAssignRequestDTO) {
        return new ResponseEntity<>(taskAssignService.assignTask(taskAssignRequestDTO), HttpStatus.OK);
    }
}
