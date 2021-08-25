package com.project.restaurant.components.orders.dtos;

import com.project.restaurant.base.dtos.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderCreateRequest extends ErrorResponse {
    private Long itemID;
    private Long userID;
}
