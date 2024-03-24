package com.shoesapp.order.service.impl;

import com.shoesapp.cart.entity.Cart;
import com.shoesapp.cart.repository.CartRepository;
import com.shoesapp.cart.service.CartService;
import com.shoesapp.cartItem.entity.CartItem;
import com.shoesapp.exception.APIException;
import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.order.dto.OrderDTO;
import com.shoesapp.order.entity.Order;
import com.shoesapp.order.repository.OrderRepository;
import com.shoesapp.order.service.OrderService;
import com.shoesapp.orderItem.dto.OrderItemDTO;
import com.shoesapp.orderItem.entity.OrderItem;
import com.shoesapp.orderItem.repository.OrderItemRepository;
import com.shoesapp.payment.entity.Payment;
import com.shoesapp.payment.repository.PaymentRepository;
import com.shoesapp.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ModelMapper mapper;

    @Override
    public Page<Order> getAllOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }

    @Override
    public List<OrderDTO> getAllOrdersByUser(String emailId) {
        List<Order> ordersFromDB = orderRepository.findAllByEmail(emailId);

        List<OrderDTO> orderDTOS = ordersFromDB.stream().map(order -> mapper.map(order, OrderDTO.class)).toList();

        if(orderDTOS.isEmpty()){
            throw new APIException("There is no any order!");
        }

        return orderDTOS;
    }

    @Override
    public OrderDTO getOrderByUser(String email, Long orderId) {
        Order order = orderRepository.findOrdersByEmailAndOrderId(email, orderId);

        if(order == null){
            throw new ResourceNotFoundException("Order with Id: " + orderId + " not found!");
        }

        return mapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO placeOrder(String emailId, Long cartId, String paymentMethod) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);

        if(cart == null){
            throw new APIException("Cart with Id: " + cartId + " not found!");
        }

        Order order = new Order();
        order.setEmail(emailId);
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Order Accepted!");

        Payment payment = new Payment();
        payment.setPaymentMethod(paymentMethod);
        payment.setOrder(order);

        paymentRepository.save(payment);

        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        List<CartItem> cartItems = cart.getCartItems();

        if(cartItems.isEmpty()){
            throw new APIException("Cart is Empty!");
        }

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();

            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductsPrice(cartItem.getProductPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(savedOrder);

            orderItems.add(orderItem);
        }

        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getCartItems().forEach(item -> {
            int quantity = item.getQuantity();

            Product product = item.getProduct();

            cartService.deleteProductFromCart(cartId, item.getProduct().getProductId());

            product.setQuantity(product.getQuantity() - quantity);
        });

        OrderDTO orderDTO = mapper.map(savedOrder, OrderDTO.class);

        orderItems.forEach(item -> orderDTO.getOrderItemsDTO().add(mapper.map(item, OrderItemDTO.class)));

        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder(String emailId, Long orderId, String orderstatus) {
        Order order = orderRepository.findOrdersByEmailAndOrderId(emailId, orderId);

        if(order == null){
            throw new ResourceNotFoundException("Order with Id: " + orderId + " not found!");
        }

        order.setOrderStatus(orderstatus);

        return mapper.map(order, OrderDTO.class);
    }


}
