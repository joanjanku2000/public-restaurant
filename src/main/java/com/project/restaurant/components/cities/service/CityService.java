package com.project.restaurant.components.cities.service;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.entities.City;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityService {
    void save(CityCreateRequest city);

    City find(Long id);

    void delete(Long id);

    Page<City> getAll(PageParams pageParams);

    List<City> filter(SearchCriteria searchCriteria);

    List<City> findAll();

    City findByName(String name);
}
