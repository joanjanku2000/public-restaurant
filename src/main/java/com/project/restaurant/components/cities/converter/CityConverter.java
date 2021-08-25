package com.project.restaurant.components.cities.converter;

import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.dtos.CityDto;
import com.project.restaurant.components.cities.entities.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityConverter implements Mapper<CityDto, City> {

	@Autowired
	ModelMapper modelMapper;

	public static City requestToEntity(CityCreateRequest request) {
		return new City(request.getName());
	}

	@Override
	public CityDto toDto(City b) {
		return modelMapper.map(b, CityDto.class);
	}

	@Override
	public List<CityDto> toDtoList(List<City> b) {
		return b.stream().map((this::toDto)).collect(Collectors.toList());
	}
}
