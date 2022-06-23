package com.etaskify.controller;


import com.etaskify.dto.request.OrganizationRequestDTO;
import com.etaskify.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "endpoint for organizations")
@RequestMapping("${app.root.url}/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    @ApiOperation("create organization profile and create admin user")
    public ResponseEntity<?> create(@Valid @RequestBody OrganizationRequestDTO organizationRequestDTO) {
        return new ResponseEntity<>(organizationService.create(organizationRequestDTO), HttpStatus.CREATED);
    }
}
