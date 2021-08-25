package com.project.restaurant.items.unit;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.ingredients.entities.Ingredient;
import com.project.restaurant.components.ingredients.service.IngredientServiceImpl;
import com.project.restaurant.components.items.converter.ItemConverter;
import com.project.restaurant.components.items.dtos.ItemCreateRequest;
import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.items.exceptions.ItemBadRequestException;
import com.project.restaurant.components.items.repository.ItemRepository;
import com.project.restaurant.components.items.service.ItemServiceImpl;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.repository.OrderRepository;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ItemTests {
    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private IngredientServiceImpl ingredientService;


    @Mock
    private ItemConverter itemConverter;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContextHolder securityContextHolder;

    @InjectMocks
    private ItemServiceImpl itemService;


    @Test
    @DisplayName("Saving success")
    public void saveSuccess() {
        Long[] ids = {1L};
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();
        itemCreateRequest.setName("Item");
        itemCreateRequest.setDescription("Description");
        itemCreateRequest.setPrice(BigDecimal.TEN);
        itemCreateRequest.setIngredientIds(Arrays.asList(ids));

        User user = new User();
        user.setId(1L);
        user.setFullName("User");

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Ingredient");
        ingredient.setCalories(20.2);

        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient);
        Item item = getItem();

        setAuthentication();
        Mockito.when(ingredientService.findById(1L))
                .thenReturn(ingredient);
        Mockito.when(userService.findByEmail("email"))
                .thenReturn(user);
        Mockito.when(itemConverter.requestToEntity(itemCreateRequest, user, ingredientList, ""))
                .thenReturn(item);

        MockMultipartFile multipartFile = new MockMultipartFile("path", "image".getBytes());

        itemService.save(itemCreateRequest, multipartFile);

        Mockito.verify(itemRepository, Mockito.times(1)).save(item);

        log.debug("Save success : For request {} itemRepository.save(..) was invoked 1 time", itemCreateRequest);
    }

    private Item getItem() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient");
        ingredient.setCalories(20.2);
        User seller = new User();
        seller.setId(1L);
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient);

        Item item = new Item();
        item.setId(1L);
        item.setName("Item");
        item.setDeleted(false);
        item.setDescription("Description");
        item.setPrice(BigDecimal.TEN);
        item.setIngredients(ingredientList);
        item.setSeller(seller);
        return item;
    }

    @Test
    @DisplayName("Saving failure")
    public void saveFailure() {
        Long[] ids = {1L};
        ItemCreateRequest itemCreateRequest = new ItemCreateRequest();

        itemCreateRequest.setDescription("Description");
        itemCreateRequest.setPrice(BigDecimal.TEN);
        itemCreateRequest.setIngredientIds(Arrays.asList(ids));


        try {
            itemService.save(itemCreateRequest, null);
        } catch (ItemBadRequestException e) {
            Mockito.verify(itemRepository, Mockito.times(0)).save(Mockito.any(Item.class));
            log.error("Thrown exception: {} ", e.getMessage());
            log.debug("Save failure : For request {} itemRepository.save(..) was invoked 0 time, name is missing", itemCreateRequest);
        }

    }

    @Test
    @DisplayName("Finding item by id")
    public void findById() {
        Long id = 1L;
        Item item = getItem();

        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Assertions.assertEquals(itemService.findById(id).getName(), item.getName());

        log.debug("Find item by id: Success {}", item);
    }

    @Test
    @DisplayName("Deleting ingredient from item")
    public void deleteIngredientSuccess() {
        Long itemId = 1L;
        Long ingredientId = 1L;
        Item item = getItem();

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        itemService.deleteIngredient(itemId, ingredientId);
        Assertions.assertEquals(0, item.getIngredients().size());

        log.debug("Delete ingredient Success : Deleted ingredient with id {} from item {}", ingredientId, item);
    }

    @Test
    @DisplayName("Delete item by id: not deleted")
    public void deleteItemFail() {
        Long itemId = 1L;
        Item item = getItem();
        Orders orders = new Orders();
        orders.setId(1L);
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(orders);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Mockito.when(orderRepository.findAllByItemIdAndDelivered(itemId, true))
                .thenReturn(ordersList);

        Assertions.assertThrows(BadRequestException.class, () -> itemService.delete(itemId));

        log.debug("Delete item failed: Test Success");
    }

    @Test
    @DisplayName("Delete item by id: deleted")
    public void deleteItemSuccess() {
        Long itemId = 1L;
        Item item = getItem();

        List<Orders> ordersList = new ArrayList<>();

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Mockito.when(orderRepository.findAllByItemIdAndDelivered(itemId, true)).thenReturn(ordersList);

        itemService.delete(itemId);

        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
        Assertions.assertTrue(item.isDeleted());
        log.debug("Delete item success: Item deletion verified and save method of repo invoked only once , item = {}", item);
    }

    @Test
    @DisplayName("Add ingredient to item")
    public void addIngredientToItem() {
        Long itemId = 1L;
        Long ingredientId = 2L;
        Item item = getItem();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setName("Ingredient2");
        ingredient.setCalories(34.2);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        Mockito.when(ingredientService.findById(ingredientId)).thenReturn(ingredient);

        itemService.addIngredient(itemId, ingredientId);

        Assertions.assertEquals(2, item.getIngredients().size());

        log.info("Add ingredient to item: Success => Distinct ingredient added -> Test passed {}", item);
    }

    @Test
    @DisplayName("Add ingredient to item : Fail -> ingredient exists")
    public void addIngredientToItemFail() {
        Long itemId = 1L;
        Long ingredientId = 1L;
        Item item = getItem();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
//        Mockito.when(ingredientService.findById(ingredientId)).thenReturn(ingredient);
        itemService.addIngredient(itemId, ingredientId);

        Assertions.assertEquals(1, item.getIngredients().size());

        log.info("Add ingredient to item : Fail : Test Success => Existing ingredient added -> Item = {}", item);
    }

    private void setAuthentication() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("email", null, authorities));
    }
}
