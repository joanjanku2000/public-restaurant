package com.project.restaurant.components.items.converter;

import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.ingredients.converter.IngredientConverter;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.dtos.ItemDto;
import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemConverter implements Mapper<ItemDto, Item> {

    @Autowired
    private IngredientConverter ingredientConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private ModelMapper modelMapper;

    public Item requestToEntity(ItemCreateRequest itemCreateRequest, User seller, List<Ingredient> ingredients, String pictureName) {
        return new Item(itemCreateRequest.getName(), itemCreateRequest.getDescription(),
                itemCreateRequest.getPrice(), ingredients, seller, pictureName, itemCreateRequest.getVersion());

    }

    public ItemDto toDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    @Override
    public List<ItemDto> toDtoList(List<Item> b) {
        return b.stream().map((this::toDto)).collect(Collectors.toList());
    }

    public Page<ItemDto> toDtoPage(Page<Item> items) {
        return items.map(this::toDto);
    }
}
