package com.project.restaurant.components.roles.repository;

import com.project.restaurant.components.roles.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles,Long> {
	List<Roles> findAllByName(String name);
}
