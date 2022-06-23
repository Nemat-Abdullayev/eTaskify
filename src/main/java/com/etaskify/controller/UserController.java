package com.etaskify.controller;


import com.etaskify.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "endpoint for users")
@PreAuthorize("hasAnyRole('ROLE_USER')")
@RequestMapping("${app.root.url}/user")
public class UserController {

    private final TaskService taskService;

    @GetMapping("/task")
    @ApiOperation("get all task of user")
    public ResponseEntity<?> getTasks() {
        return new ResponseEntity<>(taskService.getTasksForUsers(), HttpStatus.OK);
    }


}
