package com.shoesapp.cartItem.dto;

import com.shoesapp.cart.dto.CartDTO;
import com.shoesapp.product.dto.ProductDTO;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long cartItemId;
    private Integer quantity;
    private double discount;
    private double productPrice;
    private CartDTO cartDTO;
    private ProductDTO productDTO;
}
