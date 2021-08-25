package com.project.restaurant.components.cities.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CityDto {
	private Long id;
	private String name;


	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
