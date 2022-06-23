package com.etaskify.controller;


import com.etaskify.dto.request.UserRequestDTO;
import com.etaskify.service.OrganizationService;
import com.etaskify.service.UserService;
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
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@Api(tags = "endpoint for admin")
@RequestMapping("${app.root.url}/admin")
public class AdminController {

    private final UserService userService;

    @PostMapping("/user")
    @ApiOperation("create user for organization if role is admin")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.create(userRequestDTO), HttpStatus.OK);
    }

}
