package com.project.restaurant.orders.unit;

import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.items.repository.ItemRepository;
import com.project.restaurant.components.orders.dtos.OrderCreateRequest;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.repository.OrderRepository;
import com.project.restaurant.components.orders.service.OrderServiceImpl;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class OrdersTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;


    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void saveOrder() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setUserID(1L);
        orderCreateRequest.setItemID(1L);

        User user = new User();
        user.setId(1L);
        user.setFullName("fullname");
        user.setDeleted(false);

        Item item = new Item();
        item.setId(1L);
        item.setName("Item Name");
        item.setPrice(BigDecimal.TEN);

        when(userRepository.findById(orderCreateRequest.getUserID())).thenReturn(Optional.of(user));
        when(itemRepository.findById(orderCreateRequest.getItemID())).thenReturn(Optional.of(item));

        orderService.saveOrder(orderCreateRequest);

        Mockito.verify(orderRepository, Mockito.times(1)).save(any(Orders.class));
    }

    @Test
    public void provideFeedback() {
        String feedback = "feedback";

        Orders orders = new Orders();
        orders.setId(1L);
        orders.setDelivered(true);
        orders.setActive(false);
        orders.setDeleted(false);

        when(orderRepository.findById(orders.getId())).thenReturn(Optional.of(orders));

        orderService.provideFeedback(orders.getId(), feedback);

        verify(orderRepository, times(1)).save(orders);
        Assertions.assertEquals(feedback, orders.getFeedback());

    }


}
