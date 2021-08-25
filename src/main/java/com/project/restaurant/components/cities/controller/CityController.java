package com.project.restaurant.components.cities.controller;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.cities.converter.CityConverter;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.dtos.CityDto;
import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.cities.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {

	private final CityService cityServiceImpl;
	private final CityConverter cityConverter;

	@Autowired
	public CityController(CityService cityServiceImpl, CityConverter cityConverter) {
		this.cityConverter = cityConverter;
		this.cityServiceImpl = cityServiceImpl;
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.OK)
	public void save(@Valid @RequestBody CityCreateRequest req) {
		cityServiceImpl.save(req);
	}
	
	@GetMapping
	public ResponseEntity<City> find(@RequestParam Long id) {
		return ResponseEntity.ok(cityServiceImpl.find(id));
	}

	@GetMapping("/all")
	public ResponseEntity<Page<City>> getAll(PageParams pageParams) {
		return ResponseEntity.ok(cityServiceImpl.getAll(pageParams));
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void delete(@RequestParam Long id) {
		cityServiceImpl.delete(id);
	}

	@GetMapping("/filter")
    public ResponseEntity<List<CityDto>> filter(@RequestBody SearchCriteria searchCriteria) {
        log.info("Search criteria : {} ", searchCriteria);
        return ResponseEntity.ok(cityConverter.toDtoList(cityServiceImpl.filter(searchCriteria)));
    }
}
