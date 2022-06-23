package com.etaskify.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private static final long serialVersionUID = 270383254869437136L;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    @NotBlank(message = "username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "please enter a valid email address")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    @Length(min = 6,message = "password length must be 6 or greater than")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "password must be alphanumeric")
    private String password;
}
