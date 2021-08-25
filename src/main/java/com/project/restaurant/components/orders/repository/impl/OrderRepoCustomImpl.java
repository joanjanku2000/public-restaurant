package com.project.restaurant.components.orders.repository.impl;

import com.project.restaurant.components.orders.entities.Orders;
import com.project.restaurant.components.orders.repository.OrderRepoCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OrderRepoCustomImpl implements OrderRepoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Orders> listOrdersByUserIdAndTime(Long uid){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Orders> ordersCriteriaQuery = cb.createQuery(Orders.class);

        Root<Orders> order = ordersCriteriaQuery.from(Orders.class);
        order.fetch("item").fetch("ingredients");
        Predicate userIdPredicate = cb.equal(
                order.join("user").get("id")
                ,uid);

        Order dateTime = cb.desc(
                order.get("dateTime"));

        ordersCriteriaQuery
                .select(order)
                .where(userIdPredicate)
                .orderBy(dateTime);

        TypedQuery<Orders> query =
                entityManager.createQuery(ordersCriteriaQuery);

        return query.getResultList();
    }
}
