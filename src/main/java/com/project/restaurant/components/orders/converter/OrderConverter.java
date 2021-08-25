package com.project.restaurant.components.orders.converter;


import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.items.converter.ItemConverter;
import com.project.restaurant.components.items.entities.Item;
import com.project.restaurant.components.orders.dtos.OrdersDto;
import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter implements Mapper<OrdersDto, Orders> {

    @Autowired
    private ItemConverter itemConverter;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private ModelMapper modelMapper;

    public static Orders toEntity(User user, Item item) {
        return new Orders(LocalDateTime.now(), "",
                false, user, item, true);
    }


    public List<OrdersDto> toDtoList(List<Orders> orders) {
        return orders.stream().map(
                this::toDto
        ).collect(Collectors.toList());
    }

    @Override
    public OrdersDto toDto(Orders b) {
        return modelMapper.map(b, OrdersDto.class);
    }
}
