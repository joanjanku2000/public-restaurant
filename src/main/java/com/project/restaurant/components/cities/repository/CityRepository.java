package com.project.restaurant.components.cities.repository;

import com.project.restaurant.components.cities.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
	List<City> findAllByName(String name);

}
