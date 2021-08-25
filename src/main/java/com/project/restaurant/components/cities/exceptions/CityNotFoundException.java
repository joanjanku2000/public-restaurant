package com.project.restaurant.components.cities.exceptions;

import com.project.restaurant.base.exceptions.NotFoundException;

public class CityNotFoundException extends NotFoundException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
