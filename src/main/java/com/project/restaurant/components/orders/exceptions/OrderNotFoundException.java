package com.project.restaurant.components.orders.exceptions;

import com.project.restaurant.base.exceptions.NotFoundException;

public class OrderNotFoundException extends NotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
