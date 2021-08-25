package com.project.restaurant.components.user.dtos;

import com.project.restaurant.base.dtos.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest extends ErrorResponse {
    @NotNull
    @NotBlank(message = "Full Name is mandatory")
    private String fullname;
    private String phoneNumber;

    @NotNull(message = "Role is mandatory")
    private Long roleId;

    private String address;
    @NotNull
    @NotBlank(message = "Email is necessary for registration")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",message = "Please provide a valid email address,otherwise no registration broskii ;)")
    private String email;
    @NotNull
    @NotBlank(message = "Password is necessary for registration")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$" ,
//            message = "Password must contain at least: one digit [0-9].\n" +
//            ",one lowercase Latin character [a-z].\n" +
//            ", uppercase Latin character [A-Z].\n" +
//            ",one special character like ! @ # & ( ).\n" +
//            "Password must contain a length of at least 8 characters and a maximum of 20 characters.")
    private String password;
    private Long cityId;
}
