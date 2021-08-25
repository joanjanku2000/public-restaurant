package com.project.restaurant.components.ingredients.controller;

import com.project.restaurant.components.ingredients.converter.IngredientConverter;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.dtos.IngredientDto;
import com.project.restaurant.components.ingredients.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientConverter ingredientConverter;

    @PostMapping("/save")
    public void save(@Valid @RequestBody IngredientCreateRequest ingredientCreateRequest) {
        ingredientService.save(ingredientCreateRequest);
    }

    @GetMapping
    public ResponseEntity<IngredientDto> find(@RequestParam Long id) {
        return ResponseEntity.ok(ingredientConverter.toDto(ingredientService.findById(id)));
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<IngredientDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(ingredientConverter.toDto(ingredientService.findByName(name)));
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id){
        ingredientService.delete(id);
    }
}
