package com.project.restaurant.components.ingredients.dtos;

import com.project.restaurant.base.dtos.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class IngredientCreateRequest extends ErrorResponse {
    @NotNull
    @NotBlank(message = "Name cannot be left blank")
    private final String name;

    private final Double calories;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof IngredientCreateRequest)) return false;

        IngredientCreateRequest ingredientCreateRequest = (IngredientCreateRequest) o;

        return ingredientCreateRequest.getName().equals(this.getName())
                && ingredientCreateRequest.getCalories().equals(this.getCalories());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
