package com.project.restaurant.components.orders.exceptions;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.orders.dtos.OrderCreateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBadRequestException extends BadRequestException {
    private final transient OrderCreateRequest orderCreateRequest;
    private final transient String field;

    public OrderBadRequestException(String message, OrderCreateRequest orderCreateRequest, String field) {
        super(message);
        this.orderCreateRequest = orderCreateRequest;
        this.field = field;
    }
}
