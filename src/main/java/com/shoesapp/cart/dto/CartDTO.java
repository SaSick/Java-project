package com.shoesapp.cart.dto;

import com.shoesapp.product.dto.ProductDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private Double totalPrice;
    private List<ProductDTO> productDTOS = new ArrayList<>();
}
