package com.project.restaurant.components.orders.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.restaurant.components.items.dtos.ItemDto;
import com.project.restaurant.components.user.dtos.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class OrdersDto {
	private Long id;
	private LocalDateTime dateTime;
	private String feedback;
	private boolean delivered;
	private UserDto user;
	@JsonIgnoreProperties("ingredients")
	private ItemDto item;
}
