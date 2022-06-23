package com.etaskify.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("model for user")
public class UserDTO {

    private String name;
    private String surname;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "please enter a valid email address")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "password must be alphanumeric")
    @Length(min = 6,message = "password Length must be 6 or greater than")
    private String password;
}
