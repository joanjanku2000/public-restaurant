package com.project.restaurant.components.ingredients.exceptions;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientBadRequestException extends BadRequestException {
    private final transient IngredientCreateRequest ingredientCreateRequest;
    private final String fieldName;


    public IngredientBadRequestException(String message, IngredientCreateRequest ingredientCreateRequest, String fieldName) {
        super(message);
        this.ingredientCreateRequest = ingredientCreateRequest;
        this.fieldName = fieldName;
    }
}
