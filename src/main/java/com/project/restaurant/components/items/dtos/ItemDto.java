package com.project.restaurant.components.items.dtos;

import com.project.restaurant.components.ingredients.dtos.IngredientDto;
import com.project.restaurant.components.user.dtos.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private List<IngredientDto> ingredients;

    private UserDto seller;

    private String picture;

    public ItemDto(Long id, String name, String description, BigDecimal price, List<IngredientDto> ingredients, UserDto seller, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
        this.seller = seller;
        this.picture = image;
    }

}
