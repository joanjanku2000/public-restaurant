package com.project.restaurant.components.ingredients.converter;

import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.dtos.IngredientDto;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientConverter implements Mapper<IngredientDto, Ingredient> {
    @Autowired
    private ModelMapper modelMapper;

    public static Ingredient toEntity(IngredientCreateRequest ingredientCreateRequest) {
        return
                new Ingredient(ingredientCreateRequest.getName(),
                        ingredientCreateRequest.getCalories());
    }

    public IngredientDto toDto(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientDto.class);
    }

    public List<IngredientDto> toDtoList(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map((this::toDto))
                .collect(Collectors.toList());
    }
}
