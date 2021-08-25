package com.project.restaurant.components.orders.service;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.exceptions.DuplicateException;
import com.project.restaurant.base.exceptions.NotFoundException;
import com.project.restaurant.base.service.BaseService;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.items.repository.ItemRepository;
import com.project.restaurant.components.orders.converter.OrderConverter;
import com.project.restaurant.components.orders.dtos.OrderCreateRequest;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.exceptions.OrderBadRequestException;
import com.project.restaurant.components.orders.repository.OrderRepository;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.repository.UserRepository;
import com.project.restaurant.components.user.repository.impl.Filters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Filters filters;

    @Autowired
    private ItemRepository itemRepository;

    @Value("${minLength}")
    private int minlength;

    public void saveOrder(OrderCreateRequest orderCreateRequest) {
        User user
                = userRepository.findById(orderCreateRequest.getUserID())
                .orElseThrow(() ->
                        new OrderBadRequestException(ErrorMessage.NOT_FOUND + " user "
                                + " uid = " + orderCreateRequest.getUserID()
                                , orderCreateRequest, "userId"));
        Item item
                = itemRepository.findById(orderCreateRequest.getItemID())
                .orElseThrow(() ->
                        new OrderBadRequestException(ErrorMessage.NOT_FOUND + "  item "
                                + " id = " + orderCreateRequest.getItemID(), orderCreateRequest, "itemId"));

        orderRepository.save(OrderConverter.toEntity(user, item));

    }

    public Orders getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(ErrorMessage.NOT_FOUND + " order id = " + id));
    }


    public List<Orders> ordersFilter(SearchCriteria searchCriteria) {
        log.info("Fetching item filter from the database with criteria {} ", searchCriteria);
        return filters.genericFilter(Orders.class, searchCriteria, "itemingredients");
    }

    public void provideFeedback(Long id, String feedback) {
        Orders order = getOrder(id);

        log.info("Order -> {}", order);

        if (isStringValid(feedback, minlength) && order.isDelivered() && !order.isActive()) {

            if (order.getFeedback() != null && order.getFeedback().length() > minlength) {
                throw new DuplicateException("You have already provided us with feedback once.");
            }

            order.setFeedback(feedback);

        } else {
            throw new BadRequestException("This order hasn't yet been delivered.");
        }
        orderRepository.save(order);
    }

    public void deliverOrder(Long id) {
        Orders order = getOrder(id);

        if (!order.isDelivered() && order.isActive()) {
            order.setDelivered(true);
            order.setActive(false);
        } else {
            throw new DuplicateException("This order has already been delivered");
        }
        orderRepository.save(order);
    }

    public List<Orders> findOrdersOfUser(Long id) {
        return orderRepository.listOrdersByUserIdAndTime(id);
    }

}
