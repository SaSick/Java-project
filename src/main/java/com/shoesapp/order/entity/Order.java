package com.shoesapp.order.entity;

import com.shoesapp.orderItem.entity.OrderItem;
import com.shoesapp.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private String email;
    private LocalDateTime orderDate;

    private Double totalAmount;
    private String orderStatus;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
