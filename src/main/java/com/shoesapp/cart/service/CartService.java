package com.shoesapp.cart.service;

import com.shoesapp.cart.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    void updateProductInCart(Long productId, Long cartId);

    CartDTO updateProductQuantityInCart(Long productId, Long cartId, Integer quantity);

    String deleteProductFromCart(Long cartId, Long productId);

    CartDTO addProductToCart(Long cartId, Long productId, Integer quantity);
}
