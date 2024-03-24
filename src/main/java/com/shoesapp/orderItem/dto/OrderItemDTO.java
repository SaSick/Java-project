package com.shoesapp.orderItem.dto;

import com.shoesapp.order.dto.OrderDTO;
import com.shoesapp.product.dto.ProductDTO;
import lombok.Data;

@Data
public class OrderItemDTO {
    private Long orderItemId;
    private ProductDTO product;
    private Integer quantity;
    private double discount;
    private double orderedProductPrice;
}
