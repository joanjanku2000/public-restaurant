package com.project.restaurant.components.user.repository;


import com.project.restaurant.components.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, SellerRepo, JpaSpecificationExecutor<User> {
    List<User> findAllByUserDetailsCityId(Long cityId);
}
