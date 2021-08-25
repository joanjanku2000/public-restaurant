package com.project.restaurant.components.orders.service;

import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.orders.dtos.OrderCreateRequest;
import com.project.restaurant.components.orders.entities.Orders;

import java.util.List;


public interface OrderService {

     void saveOrder(OrderCreateRequest orderCreateRequest);

     Orders getOrder(Long id);

     List<Orders> ordersFilter(SearchCriteria searchCriteria);

     void provideFeedback(Long id, String feedback);

     void deliverOrder(Long id);

     List<Orders> findOrdersOfUser(Long id);

}
