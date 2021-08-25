package com.project.restaurant.ingredients.unit;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.ingredients.dtos.IngredientCreateRequest;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.ingredients.exceptions.IngredientBadRequestException;
import com.project.restaurant.components.ingredients.repository.IngredientRepository;
import com.project.restaurant.components.ingredients.service.IngredientService;
import com.project.restaurant.components.ingredients.service.IngredientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class IngredientTests {
    @Mock
    private IngredientRepository ingredientRepository;
    @InjectMocks
    private IngredientService ingredientService = new IngredientServiceImpl();

    @Test
    @DisplayName(value = "Save ingredient success")
    public void saveSuccess() {
        IngredientCreateRequest ingredientCreateRequest = new IngredientCreateRequest("ingredient", 20.3);

        when(ingredientRepository.findByName(ingredientCreateRequest.getName())).thenReturn(Optional.ofNullable(null));

        ingredientService.save(ingredientCreateRequest);

        verify(ingredientRepository, times(1)).save(any(Ingredient.class));

        log.debug("Save success : Successful invocation of save method in the repo for {} ", ingredientCreateRequest);
    }

    @Test
    @DisplayName(value = "Save ingredient failure")
    public void saveFailure() {
        IngredientCreateRequest ingredientCreateRequest = new IngredientCreateRequest("ingredient", 20.3);

        when(ingredientRepository.findByName(ingredientCreateRequest.getName()))
                .thenReturn(Optional.of(new Ingredient()));
        try {
            ingredientService.save(ingredientCreateRequest);
        } catch (IngredientBadRequestException e) {
            verify(ingredientRepository, times(0)).save(any(Ingredient.class));
            log.error("The thrown error is {} ", e.getMessage());
            log.debug("Save failure : Failure in invocation of save method in the repo for {} ", ingredientCreateRequest);
        }
    }

    @Test
    @DisplayName("Finding ingredient by id")
    public void findBydId() {
        Long id = 1l;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName("name");
        ingredient.setCalories(20.2);

        when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));

        Assertions.assertEquals(ingredientService.findById(id).getId(), id);

        log.debug("Find ingredient by id : Success found ingredient {} ", ingredient);
    }

    @Test
    @DisplayName(
            "Delete failure"
    )
    public void deleteFailure() {
        Long id = 1L;
        Ingredient ing = new Ingredient();
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ing);
        when(ingredientRepository.findItemsOfIngredient(id)).thenReturn(ingredientList);

        try {
            ingredientService.delete(id);
        } catch (BadRequestException badRequestException) {

            verify(ingredientRepository, times(0)).save(any(Ingredient.class));
            log.debug("Delete failure : Deleting ingredient with id {} threw exception {} and save method of repo was never invoked"
                    , id, badRequestException.getMessage());
        }
    }
}
