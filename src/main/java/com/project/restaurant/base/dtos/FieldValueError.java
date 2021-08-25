package com.project.restaurant.base.dtos;

import lombok.Data;

@Data
public class FieldValueError {
    private String field;
    private String error;
}
