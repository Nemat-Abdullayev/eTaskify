package com.etaskify.dto.request;

import com.etaskify.enums.RoleName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for user request")
public class UserRequestDTO {

    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "name cannot be null")
    private String surname;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "please enter a valid email address")
    private String email;


    @ApiModelProperty("role name for assign to user while creating")
    @NotNull(message = "role cannot be null,roles like this [ ADMIN,MANAGER,USER ]")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @NotNull(message = "organization id cannot be null")
    private Long organizationId;
}
