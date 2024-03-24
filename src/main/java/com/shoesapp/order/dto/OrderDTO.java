package com.shoesapp.order.dto;


import com.shoesapp.orderItem.dto.OrderItemDTO;
import com.shoesapp.product.dto.ProductDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {

    private Long orderId;
    private String email;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String orderStatus;
    private ProductDTO paymentDTO;
    private List<OrderItemDTO> orderItemsDTO = new ArrayList<>();

}
