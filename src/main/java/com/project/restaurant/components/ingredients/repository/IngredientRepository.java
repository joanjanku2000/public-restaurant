package com.project.restaurant.components.ingredients.repository;

import com.project.restaurant.components.ingredients.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepo {
    Optional<Ingredient> findByName(String name);
}
