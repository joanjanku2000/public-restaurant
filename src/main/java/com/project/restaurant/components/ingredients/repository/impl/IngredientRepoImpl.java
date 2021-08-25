package com.project.restaurant.components.ingredients.repository.impl;

import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.ingredients.repository.IngredientRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IngredientRepoImpl implements IngredientRepo {
    private static final String FIND_ITEMS =
            "select i from Ingredient i join fetch i.items where i.id = ?1 ";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Ingredient> findItemsOfIngredient(Long id) {
        return em.createQuery(FIND_ITEMS, Ingredient.class)
                .setParameter(1, id)
                .setMaxResults(5)
                .getResultList();
    }
}
