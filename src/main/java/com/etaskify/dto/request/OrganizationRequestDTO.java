package com.etaskify.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for registration request")
public class OrganizationRequestDTO {

    @ApiModelProperty("organization name")
    private String orgName;

    @ApiModelProperty("phone number")
    private String phoneNumber;

    @ApiModelProperty("address name")
    private String address;

    @Valid
    @NotNull(message = "user data cannot be null")
    @ApiModelProperty("user data")
    private UserDTO user;


}
