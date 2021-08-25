package com.project.restaurant.components.user.repository.impl;

import com.project.restaurant.components.user.entities.User;
import com.project.restaurant.components.user.repository.SellerRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class SellerRepoImpl implements SellerRepo {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String FIND_ALL_SELLERS = "select user from User user " +
            " join user.role role where role.name = 'seller' and user.enabled = true";

    @Override
    public Page<User> findAllSellers(Pageable pageable) {
        TypedQuery<User> findAllSellers
                = entityManager.createQuery(FIND_ALL_SELLERS,User.class);

         int total = findAllSellers.getResultList().size();

        return new PageImpl<>(
                entityManager.createQuery(FIND_ALL_SELLERS,User.class)
                         .setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
                         .setMaxResults(pageable.getPageSize())
                         .getResultList(),
                pageable,
                total);
    }
}
