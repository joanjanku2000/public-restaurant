package com.project.restaurant.city.unit;

import com.project.restaurant.base.exceptions.BadRequestException;
import com.project.restaurant.components.cities.dtos.CityCreateRequest;
import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.cities.exceptions.BadRequestForCityException;
import com.project.restaurant.components.cities.repository.CityRepository;
import com.project.restaurant.components.cities.service.CityService;
import com.project.restaurant.components.cities.service.CityServiceImpl;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CityTests {
    @Mock
    protected CityRepository cityRepository;
    @Mock
    protected UserRepository userRepository;


    @InjectMocks
    protected CityService cityService = new CityServiceImpl();

    @Test
    @DisplayName("Save fails, city exists")
    public void saveFailException() {
        String name = "City";

        CityCreateRequest cityCreateRequest = new CityCreateRequest();
        cityCreateRequest.setName(name);

        City c = getCity(1l);
        List<City> cities = new ArrayList<>();
        cities.add(c);

        when(cityRepository.findAllByName(name)).thenReturn(cities);

        Assertions.assertThrows(BadRequestForCityException.class, () -> cityService.save(cityCreateRequest));
        log.debug("Save failure : Throws BadRequestException when passing existing city");
    }

    @Test
    @DisplayName("Save Success")
    public void saveSuccess() {
        String name = "City";
        CityCreateRequest cityCreateRequest = new CityCreateRequest();
        cityCreateRequest.setName(name);


        List<City> cities = new ArrayList<>();

        when(cityRepository.findAllByName(name)).thenReturn(cities);
        cityService.save(cityCreateRequest);

        verify(cityRepository, times(1)).save(any(City.class));

        log.debug("Save Success : Method save of repo successfully invoked");
    }

    @Test
    @DisplayName("Find by id")
    public void findById() {
        long id = 1l;

        City c = getCity(id);

        when(cityRepository.findById(id)).thenReturn(Optional.of(c));

        Assertions.assertEquals(cityService.find(id).getId(), id);

        log.debug("Find by id : The result is the same with the one that comes from the database");
    }

    @Test
    @DisplayName("Find by name")
    public void findByName() {
        long id = 1l;
        String name = "City";

        City c = getCity(id);

        List<City> cities = new ArrayList<>();
        cities.add(c);

        when(cityRepository.findAllByName(name)).thenReturn(cities);

        Assertions.assertEquals(cityService.findByName(name).getId(), id);

        log.debug("Find by name : Successfully found user with given name");
    }


    private City getCity(long id) {
        City c = new City();
        c.setId(id);
        c.setName("City");
        c.setDeleted(false);
        return c;
    }

    @Test
    @DisplayName("Delete Fail")
    public void deleteFail() {
        long id = 1l;


        City c = getCity(id);

        User user = new User();
        user.setId(id);
        List<User> users = new ArrayList<>();
        users.add(user);

        when(cityRepository.findById(id)).thenReturn(Optional.of(c));
        when(userRepository.findAllByUserDetailsCityId(id)).thenReturn(users);

        Assertions.assertThrows(BadRequestException.class, () -> cityService.delete(id));

        log.debug("Delete fail : Deletion cannot happen because many people are registered there");
    }


}
