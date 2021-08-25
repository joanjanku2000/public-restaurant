package com.project.restaurant.components.ingredients.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class IngredientDto {
	private Long id;
	private String name;
	private Double calories;
}
