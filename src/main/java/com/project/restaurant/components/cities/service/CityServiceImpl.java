package com.project.restaurant.components.cities.service;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.base.utils.ErrorMessage;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.cities.converter.CityConverter;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.cities.exceptions.BadRequestForCityException;
import com.project.restaurant.components.cities.exceptions.CityNotFoundException;
import com.project.restaurant.components.cities.repository.CityRepository;
import com.project.restaurant.components.user.repository.UserRepository;
import com.project.restaurant.components.user.repository.impl.Filters;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class CityServiceImpl implements CityService {

    private CityRepository cityRepo;

    private Filters filters;

    private UserRepository userRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepo, Filters filters, UserRepository userRepository) {
        this.cityRepo = cityRepo;
        this.filters = filters;
        this.userRepository = userRepository;
    }

    public void save(CityCreateRequest city) {
        if (findByName(city.getName()) != null) {
            throw new BadRequestForCityException(ErrorMessage.DUPLICATE + " " + city.getName(), city);
        }
        cityRepo.save(CityConverter.requestToEntity(city));
    }

    public City find(Long id) {
        return cityRepo.findById(id)
                .orElseThrow(() ->
                        new CityNotFoundException(ErrorMessage.NOT_FOUND + " CITY ID " + id));

    }

    public City findByName(String name) {
        List<City> cities = cityRepo.findAllByName(name);
        return cities.size() == 1 ? cities.get(0) : null;
    }

    public void delete(Long id) {
        City city = find(id);
        if (!userRepository.findAllByUserDetailsCityId(id).isEmpty()) {
            throw new BadRequestException("Cannot delete this city: " + id + " since many people are registered there");
        }
        city.setDeleted(true);
        cityRepo.save(city);
    }

    public Page<City> getAll(PageParams pageParams) {
        return pageParams.getSortDirection().compareToIgnoreCase("asc") == 0 ?
                cityRepo.findAll(PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize(),
                        Sort.Direction.ASC, pageParams.getSort())) :
                cityRepo.findAll(PageRequest.of(pageParams.getPageNumber(), pageParams.getPageSize(),
                        Sort.Direction.DESC, pageParams.getSort()));
    }

    public List<City> filter(SearchCriteria searchCriteria) {
        return filters.genericFilter(City.class, searchCriteria, null);
	}

	public List<City> findAll() {
		return cityRepo.findAll();
	}
}
