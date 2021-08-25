package com.project.restaurant.components.items.controller;


import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.utils.LoggedUser;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.ingredients.dtos.IngredientDtoList;
import com.project.restaurant.components.items.converter.ItemConverter;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.dtos.ItemDto;
import com.project.restaurant.components.items.dtos.ItemUpdateRequest;
import com.project.restaurant.components.items.service.ItemService;
import com.project.restaurant.components.items.service.ItemServiceImpl;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.dtos.UserDto;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.service.UserService;
import com.project.restaurant.components.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemConverter itemConverter;
    private final UserService userService;
    private final LoggedUser loggedUser;
    private final UserConverter userConverter;

    @Autowired
    public ItemController(ItemServiceImpl itemService, UserServiceImpl userService, ItemConverter itemConverter, UserConverter userConverter) {
        this.itemService = itemService;
        this.userService = userService;
        loggedUser = new LoggedUser(userService);
        this.itemConverter = itemConverter;
        this.userConverter = userConverter;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public void save(@Valid @RequestPart ItemCreateRequest itemCreateRequest, @RequestPart MultipartFile picture) {
        log.info("Saving item {}", itemCreateRequest);
        itemService.save(itemCreateRequest, picture);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('seller')")
    public ResponseEntity<ItemDto> findById(@RequestParam Long id) {
        return ResponseEntity.ok(itemConverter.toDto(itemService.findById(id)));
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<Page<ItemDto>> findAllBySellerId(@PathVariable Long id, PageParams pageParams) {
        return ResponseEntity.ok(itemConverter.toDtoPage(itemService.findItemsOfSeller(pageParams, id)));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@PathVariable long id, @RequestBody ItemUpdateRequest itemUpdateRequest) {
        itemService.updateItem(id, itemUpdateRequest);
    }

    @PutMapping("/ingredients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addIngredients(@PathVariable Long id, @RequestBody IngredientDtoList ingredientDtoList) {
        itemService.addIngredients(id, ingredientDtoList.getIngredientIds());
    }

    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    public void deleteIngredients(@PathVariable long id, @PathVariable Long ingredientId) {
        itemService.deleteIngredient(id, ingredientId);
    }

    @GetMapping("/items")
    public ResponseEntity<Page<ItemDto>> viewItems(PageParams pageParams, @RequestParam(required = false) Long sid) {
        User user = loggedUser.getLoggedUser();
        log.debug("Logged user with id {}", user.getId());
        UserDto userDto = sid != null ? userConverter.toDto(userService.findById(sid)) :
                userConverter.toDto(user);
        Page<ItemDto> itemDtos = itemConverter.toDtoPage(
                itemService.findItemsOfSeller(pageParams, userDto.getId()));

        return ResponseEntity.ok(itemDtos);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @DeleteMapping("/delete/ingredient")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIngredient(@RequestParam Long itemId, @RequestParam Long ingredientId) {
        log.info("Removing the connection from the ingredient and item");
        itemService.removeIngredientFromItem(itemId, ingredientId);
    }

    @PatchMapping("/add-ingredient")
    public void addIngredient(@RequestParam Long itemId, @RequestParam Long ingredientId) {
        log.info("Adding the connection from the ingredient to item");
        itemService.addIngredient(itemId, ingredientId);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ItemDto>> filter(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(itemConverter.toDtoList(itemService.itemFilter(searchCriteria)));
    }
}
