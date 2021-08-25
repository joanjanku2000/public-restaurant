package com.project.restaurant.components.user.service;

import com.project.restaurant.base.dtos.PageParams;
import com.project.restaurant.base.utils.SearchCriteria;
import com.project.restaurant.components.user.dtos.UserCreateRequest;
import com.project.restaurant.components.user.dtos.UserDto;
import com.project.restaurant.components.user.dtos.UserUpdateRequest;
import com.project.restaurant.components.user.entities.User;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface UserService {
     void save(UserCreateRequest userCreateRequest);

     User findById(Long userId);

     void delete(Long userId);

     User findByEmail(String email);

     void update(Long id, UserUpdateRequest request);

     Page<User> findAllSellers(PageParams pageParams);

     List<UserDto> findAll();

     User getUser(String token);

     void registerUser(User user);

     List<User> filterSpecification(SearchCriteria searchCriteria);

     void generateAccessTokenFromRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


}
