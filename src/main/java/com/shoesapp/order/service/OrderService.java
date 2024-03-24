package com.shoesapp.order.service;

import com.shoesapp.order.dto.OrderDTO;
import com.shoesapp.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface OrderService {
    Page<Order> getAllOrders(PageRequest pageRequest);

    List<OrderDTO> getAllOrdersByUser(String emailId);

    OrderDTO getOrderByUser(String email, Long orderId);

    OrderDTO placeOrder(String emailId, Long cartId, String paymentMethod);

    OrderDTO updateOrder(String emailId, Long orderId, String orderstatus);
}
