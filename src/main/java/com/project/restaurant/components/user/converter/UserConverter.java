package com.project.restaurant.components.user.converter;

import com.project.restaurant.base.converters.Mapper;
import com.project.restaurant.components.cities.entities.City;
import com.project.restaurant.components.roles.entities.Roles;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserDto;
import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.entities.UserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements Mapper<UserDto, User> {
    @Autowired
    private ModelMapper modelMapper;

    public UserDetails toEntity(UserCreateRequest userCreateRequest, Roles role, City city, boolean enabled) {

        User user = new User(userCreateRequest.getFullname(), userCreateRequest.getPhoneNumber(), role, enabled);
        return new UserDetails(userCreateRequest.getAddress(), userCreateRequest.getEmail()
                , userCreateRequest.getPassword(), city, user);
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<UserDto> toDtoPage(Page<User> users) {
        return users.map(this::toDto);
    }
}
