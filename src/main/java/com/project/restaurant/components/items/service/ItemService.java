package com.project.restaurant.components.items.service;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.dtos.ItemUpdateRequest;
import com.project.restaurant.components.items.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ItemService {
    void save(ItemCreateRequest itemCreateRequest, MultipartFile picture);

    Item findById(Long id);

    Page<Item> findItemsOfSeller(PageParams pageParams, Long sid);

    void updateItem(Long id, ItemUpdateRequest itemUpdateRequest);

    void addIngredients(Long itemId, List<Long> ingredients);

    void deleteIngredient(Long itemId, Long ingredientId);

    void delete(Long id);

    void removeIngredientFromItem(Long itemID, Long ingredientID);

    void addIngredient(Long itemID, Long ingredientID);

    List<Item> itemFilter(SearchCriteria searchCriteria);
}
