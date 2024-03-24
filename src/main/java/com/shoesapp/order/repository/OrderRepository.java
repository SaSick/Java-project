package com.shoesapp.order.repository;

import com.shoesapp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.email = ?1 and o.orderId = ?2")
    Order findOrdersByEmailAndOrderId(String email, Long orderId);


    List<Order> findAllByEmail(String email);
}
