package com.project.restaurant.base.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class ErrorResponse {
    private List<FieldValueError> fieldValueError;
    private LocalDateTime timestamp;
}
