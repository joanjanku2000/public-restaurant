package com.project.restaurant.components.items.exceptions;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBadRequestException extends BadRequestException {
    private final transient ItemCreateRequest itemCreateRequest;
    private final String fieldName;

    public ItemBadRequestException(String message, ItemCreateRequest itemCreateRequest, String field) {
        super(message);
        this.itemCreateRequest = itemCreateRequest;
        this.fieldName = field;
    }
}
