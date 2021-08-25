package com.project.restaurant.components.orders.repository;

import com.project.restaurant.components.orders.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Orders, Long>, OrderRepoCustom {
     @Query("from Orders o where o.id = :id and o.delivered=:delivered")
     List<Orders> findAllByItemIdAndDelivered(Long id, Boolean delivered);


    @Query("from Orders o JOIN FETCH o.item i JOIN FETCH i.ingredients where o.id = :id")
     Optional<Orders> findById(@Param("id") Long id);
}
