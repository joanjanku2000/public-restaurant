package com.project.restaurant.users.unit;

import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.cities.repository.CityRepository;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.roles.repository.RolesRepository;
import com.project.restaurant.components.user.converter.UserConverter;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserUpdateRequest;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.UserDetails;
import com.project.restaurant.components.user.repository.UserDetailsRepository;
import com.project.restaurant.components.user.repository.UserRepository;
import com.project.restaurant.components.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsersTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserConverter userConverter;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private RolesRepository rolesRepository;


    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void test_Save() {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setFullname("FullName");
        userCreateRequest.setPhoneNumber("12345678");
        userCreateRequest.setPassword("1234");
        userCreateRequest.setRoleId(1L);
        userCreateRequest.setCityId(1L);
        userCreateRequest.setAddress("Address");

        Roles roles = new Roles();
        roles.setId(userCreateRequest.getRoleId());
        roles.setName("roles");
        roles.setDeleted(false);

        City city = new City();
        city.setId(userCreateRequest.getCityId());
        city.setName("city");
        city.setDeleted(false);

        UserDetails user = new UserDetails();
        user.setUser(new User());
        user.setId(1L);
        user.setPassword(userCreateRequest.getPassword());
        user.setEmail(userCreateRequest.getEmail());
        user.setDeleted(false);
        user.setCity(city);
        user.getUser().setRole(roles);

        when(rolesRepository.findById(roles.getId())).thenReturn(Optional.of(roles));
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
        when(userConverter.toEntity(userCreateRequest, roles, city, false)).thenReturn(user);

        userService.save(userCreateRequest);

        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));
    }

    @Test
    public void findById() {
        User user1 = new User();
        user1.setId(1L);
        Long userId = 1L;
        UserDetails user = new UserDetails();
        user.setUser(user1);
        user.setId(1L);
        user.setPassword("1234");
        user.setEmail("email");
        user.setDeleted(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user.getUser()));

        Assertions.assertEquals(userId, userService.findById(userId).getId());
    }

    @Test
    public void updateTest() {
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        Long userId = 1L;
        userUpdateRequest.setPhoneNumber("1234");

        User user1 = new User();
        user1.setId(1L);

        Long uid = 1L;
        UserDetails user = new UserDetails();
        user.setUser(user1);
        user.setId(1L);
        user.setPassword("1234");
        user.setEmail("email");
        user.setDeleted(false);

        City city = new City();
        city.setId(1L);
        city.setName("city");
        city.setDeleted(false);
        setAuthentication();

        Mockito.when(userDetailsRepository.findByEmail("email")).thenReturn(Optional.of(user));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user.getUser()));

        userService.update(uid, userUpdateRequest);

        verify(userRepository, times(1)).save(any(User.class));
    }

    private void setAuthentication() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("email", null, authorities));
    }

}
