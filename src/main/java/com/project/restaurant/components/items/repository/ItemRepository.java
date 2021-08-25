package com.project.restaurant.components.items.repository;

import com.project.restaurant.components.items.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "from Item i join fetch i.ingredients ing where i.seller.id = :id"
            , countQuery = "select count(i) from Item i where i.id = :id")
    Page<Item> findAllBySellerId(Pageable pageable, @Param("id") Long Id);

    @Query("select item from Item item JOIN FETCH item.ingredients where item.id = :id")
    Optional<Item> findById(@Param("id") Long id);

    @Query(value = "from Item i join fetch i.ingredients ing where i.seller.id = :id"
            , countQuery = "select count(i) from Item i where i.id = :id")
    List<Item> findAllBySellerId(Long id);


}
