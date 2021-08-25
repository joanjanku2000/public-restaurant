package com.project.restaurant.components.ingredients.service;

import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.entities.Ingredient;

import java.util.List;


public interface IngredientService {
    void save(IngredientCreateRequest request);

    Ingredient findByName(String name);

    Ingredient findById(Long id);

    List<Ingredient> findAll();

    void delete(Long id);
}
