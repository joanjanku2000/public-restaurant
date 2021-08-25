package com.project.restaurant.components.user.exceptions;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBadRequestException extends BadRequestException {
    private final transient UserCreateRequest userCreateRequest;
    private final String field;

    public UserBadRequestException(String message, UserCreateRequest userCreateRequest, String field) {
        super(message);
        this.userCreateRequest = userCreateRequest;
        this.field = field;
    }
}
