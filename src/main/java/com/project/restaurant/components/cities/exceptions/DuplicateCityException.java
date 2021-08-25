package com.project.restaurant.components.cities.exceptions;

import com.project.restaurant.base.exceptions.DuplicateException;

public class DuplicateCityException extends DuplicateException {
    public DuplicateCityException(String message) {
        super(message);
    }
}
