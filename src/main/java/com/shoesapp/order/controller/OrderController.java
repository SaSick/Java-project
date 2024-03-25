package com.shoesapp.order.controller;

import com.shoesapp.order.dto.OrderDTO;
import com.shoesapp.order.dto.OrderResponse;
import com.shoesapp.order.entity.Order;
import com.shoesapp.order.service.OrderService;
import com.shoesapp.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public OrderResponse<Order> getAllOrders(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    )
    {
        Page<Order> pagedResponse = orderService.getAllOrders(PageRequest.of(page, size));

        OrderResponse<Order> orderResponse = new OrderResponse<>();

        orderResponse.setData(pagedResponse.getContent());
        orderResponse.setTotal(pagedResponse.getTotalElements());

        return orderResponse;
    }

    @GetMapping("/users/{emailId}/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByUser(
            @PathVariable("emailId") String emailId
    ){
        List<OrderDTO> orderDTO = orderService.getAllOrdersByUser(emailId);
        return new ResponseEntity<>(orderDTO, HttpStatus.FOUND);
    }

    @GetMapping("users/{emailId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByUser(
            @PathVariable("emailId") String email,
            @PathVariable("orderId") Long orderId
    ){
        OrderDTO orderDTO = orderService.getOrderByUser(email, orderId);
        return ResponseEntity.status(HttpStatus.FOUND).body(orderDTO);
    }

    @PostMapping("/users/{emailId}/carts/{cartId}/payments/{paymentMethod}/order")
    public ResponseEntity<OrderDTO> orderProducts(
            @PathVariable("emailId") String emailId,
            @PathVariable("cartId") Long cartId,
            @PathVariable("paymentMethod") String paymentMethod
    ){
        OrderDTO orderDTO = orderService.placeOrder(emailId, cartId, paymentMethod);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @PutMapping("/users/{emailId}/orders/{orderId}/orderStatus/{orderStatus}")
    public ResponseEntity<OrderDTO> updateOrderByUser(
            @PathVariable("emailId") String emailId,
            @PathVariable("orderId") Long orderId,
            @PathVariable("orderStatus") String orderstatus
    ){
        OrderDTO orderDTO = orderService.updateOrder(emailId, orderId, orderstatus);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
