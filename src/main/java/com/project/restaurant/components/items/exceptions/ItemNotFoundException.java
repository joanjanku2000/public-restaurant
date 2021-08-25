package com.project.restaurant.components.items.exceptions;

import com.project.restaurant.base.exceptions.NotFoundException;

public class ItemNotFoundException extends NotFoundException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
