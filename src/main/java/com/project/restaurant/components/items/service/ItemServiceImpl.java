package com.project.restaurant.components.items.service;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.exceptions.NotFoundException;
import com.project.restaurant.base.service.BaseService;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.base.utils.LoggedUser;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.ingredients.service.IngredientServiceImpl;
import com.project.restaurant.components.items.converter.ItemConverter;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.dtos.ItemUpdateRequest;
import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.items.exceptions.ItemBadRequestException;
import com.project.restaurant.components.items.repository.ItemRepository;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.repository.OrderRepository;
import com.project.restaurant.components.user.repository.impl.Filters;
import com.project.restaurant.components.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.project.restaurant.base.utils.FileUtil.uploadFile;

@Slf4j
@Service
@Transactional
public class ItemServiceImpl extends BaseService implements ItemService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final IngredientServiceImpl ingredientServiceImpl;
    private final LoggedUser loggedUser;
    private final ItemConverter itemConverter;
    private final Filters filters;
    @Value("${minLength}")
    private int minLength;
    @Value("${uploadDir}")
    private String uploadDir;

    public ItemServiceImpl(ItemRepository itemRepository,
                           OrderRepository orderRepository, IngredientServiceImpl ingredientServiceImpl,
                           UserService userServiceImpl, ItemConverter itemConverter, Filters filters) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.ingredientServiceImpl = ingredientServiceImpl;
        loggedUser = new LoggedUser(userServiceImpl);
        this.itemConverter = itemConverter;
        this.filters = filters;
    }


    private List<Ingredient> findIngredients(List<Long> idList) {
        List<Ingredient> ingredientList = new ArrayList<>();
        idList.forEach(id -> ingredientList.add(ingredientServiceImpl.findById(id)));
        return ingredientList;
    }


    public void save(ItemCreateRequest itemCreateRequest, MultipartFile picture) {

        if (!isStringValid(itemCreateRequest.getName(), minLength)) {
            throw new ItemBadRequestException(ErrorMessage.EMPTY.toString(), itemCreateRequest, "name");
        }
        if (!isStringValid(itemCreateRequest.getDescription(), minLength)) {
            throw new ItemBadRequestException(ErrorMessage.EMPTY.toString(), itemCreateRequest, "description");
        }
        if (itemCreateRequest.getPrice() == null) {
            throw new ItemBadRequestException(ErrorMessage.EMPTY.toString(), itemCreateRequest, "price");
        }

        if (picture != null && !picture.isEmpty()) {
            uploadFile(uploadDir, picture);
        }

        log.info("Logged user = {}", loggedUser.getLoggedUser());

        Item item = picture != null ?
                itemConverter.requestToEntity(itemCreateRequest, loggedUser.getLoggedUser(),
                        findIngredients(itemCreateRequest.getIngredientIds()), picture.getOriginalFilename())
                :
                itemConverter.requestToEntity(itemCreateRequest,
                        loggedUser.getLoggedUser(),
                        findIngredients(itemCreateRequest.getIngredientIds()), "");

        log.info("Item {} being saved", item);

        itemRepository.save(item);
    }


    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Item with id " + id + " " + ErrorMessage.NOT_FOUND));
    }

    public Page<Item> findItemsOfSeller(PageParams pageParams, Long sid) {
        Pageable pageable = pageParams.getSortDirection().compareToIgnoreCase("asc") == 0
                ? PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize()
                , Sort.Direction.ASC, pageParams.getSort()) :
                PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize()
                        , Sort.Direction.DESC, pageParams.getSort());

        return itemRepository.findAllBySellerId(pageable, sid);
    }


    public void updateItem(Long id, ItemUpdateRequest itemUpdateRequest) {
        Item item = findById(id);

        if (isStringValid(itemUpdateRequest.getName(), minLength)) {
            item.setName(itemUpdateRequest.getName());
        }
        if (isStringValid(itemUpdateRequest.getDescription(), minLength)) {
            item.setDescription(itemUpdateRequest.getDescription());
        }
        if (itemUpdateRequest.getPrice() != null) {
            item.setPrice(itemUpdateRequest.getPrice());
        }

        itemRepository.save(item);
    }

    public void addIngredients(Long itemId, List<Long> ingredients) {
        Item item = findById(itemId);
        List<Ingredient> ingredientList = findIngredients(ingredients);
        List<Ingredient> existingIngredients = item.getIngredients();
        removeDuplicatesFromIngredients(ingredientList, existingIngredients);
        item.setIngredients(
                Stream.concat(existingIngredients.stream()
                        , ingredientList.stream())
                        .collect(Collectors.toList()));
        itemRepository.save(item);
    }

    private void removeDuplicatesFromIngredients(List<Ingredient> ingredientList, List<Ingredient> existingIngredients) {
        for (Ingredient existingIngredient : existingIngredients) {
            for (int j = 0; j < ingredientList.size(); j++) {
                if (existingIngredient.getId().
                        compareTo(ingredientList.get(j).getId()) == 0) {
                    ingredientList.remove(j);
                }
            }
        }
    }

    public void deleteIngredient(Long itemId, Long ingredientId) {
        Item item = findById(itemId);
        List<Ingredient> existingIngredients = item.getIngredients();
        for (int i = 0; i < existingIngredients.size(); i++) {
            if (existingIngredients.get(i).getId().compareTo(ingredientId) == 0) {
                log.info("Ingredient with id {} already exists, so it is not being added ", ingredientId);
                existingIngredients.remove(i);
            }
        }
        item.setIngredients(existingIngredients);
        itemRepository.save(item);
    }

    public void delete(Long id) {
        Item item = findById(id);
        List<Orders> ordersOfItem
                = orderRepository.findAllByItemIdAndDelivered(id, true);

        log.info("Deleting item with id {}, name {} , seller {}", item.getId(),
                item.getName(), item.getSeller().getId());

        if (doesNotHaveOrders(ordersOfItem)) {
            item.setDeleted(true);
            itemRepository.save(item);
        } else {
            throw new BadRequestException(ErrorMessage.CANNOT_DELETE + " Item has active ordersOfItem");
        }
    }

    private boolean doesNotHaveOrders(List<Orders> orders) {
        return orders.isEmpty();
    }

    public void removeIngredientFromItem(Long itemID, Long ingredientID) {
        Item item = findById(itemID);
        List<Ingredient> ingredients = item.getIngredients();

        removeIngredientFromList(ingredientID, ingredients);

        item.setIngredients(ingredients);
        itemRepository.save(item);

        log.info("Successfully removed ingredient {} from item {}", ingredientID, itemID);
    }

    private void removeIngredientFromList(Long ingredientID, List<Ingredient> ingredients) {
        // to avoid concurrentModificationException we use an array
        Ingredient[] ingredientsArray = ingredients.toArray(new Ingredient[0]);
        log.warn("Service: Removing the connection from the ingredient and item");
        for (Ingredient ingredient : ingredientsArray) {
            if (ingredient.getId().equals(ingredientID)) {
                ingredients.remove(ingredient);
            } else {
                throw new NotFoundException("Ingredient with " + ingredientID + " already not present ");
            }
        }
    }

    public void addIngredient(Long itemID, Long ingredientID) {
        Item item = findById(itemID);
        List<Ingredient> existingIngredients = item.getIngredients();
        log.warn("Service: Adding the connection from the ingredient and item");
        AtomicBoolean ingredientExists = ingredientExists(ingredientID, existingIngredients);
        if (!ingredientExists.get()) {
            Ingredient ingredientToBeAdded = ingredientServiceImpl.findById(ingredientID);
            addIngredient(item, existingIngredients, ingredientToBeAdded);
            itemRepository.save(item);
        }
    }

    private void addIngredient(Item item, List<Ingredient> ingredients, Ingredient ingredientToBeAdded) {
        ingredients.add(ingredientToBeAdded);
        item.setIngredients(ingredients);
    }

    private AtomicBoolean ingredientExists(Long ingredientID, List<Ingredient> ingredients) {
        AtomicBoolean exists = new AtomicBoolean(false);
        ingredients.forEach((ingredient -> {
            if (ingredient.getId().compareTo(ingredientID) == 0) {
                exists.set(true);
                log.info("Ingredient already part of the item , not adding it");
            }
        }));
        return exists;
    }

    public List<Item> itemFilter(SearchCriteria searchCriteria) {
        return filters.genericFilter(Item.class, searchCriteria, "ingredients");
    }
}

