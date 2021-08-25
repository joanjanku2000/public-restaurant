package com.project.restaurant.components.ingredients.service;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.components.ingredients.converter.IngredientConverter;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.ingredients.exceptions.IngredientBadRequestException;
import com.project.restaurant.components.ingredients.exceptions.IngredientNotFoundException;
import com.project.restaurant.components.ingredients.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public void save(IngredientCreateRequest request) {
        Ingredient ingredient = IngredientConverter.toEntity(request);

        Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(request.getName());

        if (ingredientOptional.isPresent()) {
            throw new IngredientBadRequestException("Ingredient " + request.getName() + " already exists", request, "name");
        }

        ingredientRepository.save(ingredient);
    }

    public Ingredient findByName(String name) {
        return ingredientRepository
                .findByName(name)
                .orElseThrow(() ->
                        new IngredientNotFoundException(ErrorMessage.NOT_FOUND + " ingredient with name " + name));
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() ->
                        new IngredientNotFoundException(ErrorMessage.NOT_FOUND + " id = " + id));
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public void delete(Long id) {
        if (!ingredientRepository.findItemsOfIngredient(id).isEmpty()) {
            throw new BadRequestException("Cannot delete, some items have it");
        }

        Ingredient ing = findById(id);
        ing.setDeleted(true);
        ingredientRepository.save(ing);
    }
}
