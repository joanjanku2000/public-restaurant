package com.project.restaurant.components.orders.repository;

import com.project.restaurant.components.orders.entities.Orders;

import java.util.List;


public interface OrderRepoCustom {
    List<Orders> listOrdersByUserIdAndTime(Long uid);
}
