package com.project.restaurant.base.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FieldValuePair {
    private String field;
    private String value;
    private String operation;
}
