package com.project.restaurant.components.roles.service;


import com.project.restaurant.components.roles.dtos.RoleCreateRequest;
import com.project.restaurant.components.roles.entities.Roles;

public interface RolesService {
    void save(RoleCreateRequest req);

    Roles findByName(String name);

    Roles findById(Long id);

    void delete(Long id);
}
