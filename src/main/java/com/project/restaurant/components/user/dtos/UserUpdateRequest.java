package com.project.restaurant.components.user.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private Long cityId;
    private String address;
}
