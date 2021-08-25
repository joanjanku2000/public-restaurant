package com.project.restaurant.components.ingredients.exceptions;

import com.project.restaurant.base.exceptions.NotFoundException;

public class IngredientNotFoundException extends NotFoundException {
    public IngredientNotFoundException(String message) {
        super(message);
    }
}
