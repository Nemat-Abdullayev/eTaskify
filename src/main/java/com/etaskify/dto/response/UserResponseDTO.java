package com.etaskify.dto.response;

import com.etaskify.enums.RoleName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for user response")
public class UserResponseDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private RoleName roleName;
}