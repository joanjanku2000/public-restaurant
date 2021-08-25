package com.project.restaurant.components.user.repository;

import com.project.restaurant.components.user.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerRepo {
     Page<User> findAllSellers(Pageable pageable);
}
