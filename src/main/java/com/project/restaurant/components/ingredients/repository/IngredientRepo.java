package com.project.restaurant.components.ingredients.repository;

import com.project.restaurant.components.ingredients.entities.Ingredient;

import java.util.List;

public interface IngredientRepo {
    List<Ingredient> findItemsOfIngredient(Long id);
}
