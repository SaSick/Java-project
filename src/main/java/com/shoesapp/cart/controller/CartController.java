package com.shoesapp.cart.controller;

import com.shoesapp.cart.dto.CartDTO;
import com.shoesapp.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public ResponseEntity<List<CartDTO>> getAllCarts(){
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return ResponseEntity.status(HttpStatus.OK).body(cartDTOS);
    }

    @GetMapping("{emailId}/cart/{cartId}")
    public ResponseEntity<CartDTO> getCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("emailId") String emailId
    ){
        CartDTO cartDTO = cartService.getCart(emailId, cartId);
        return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
    }

    @PutMapping("cart/{cartId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("productId") Long productId,
            @PathVariable("quantity") Integer quantity
    ){
        cartService.addProductToCart(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("cart/{cartId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> updateCartProduct(
            @PathVariable("productId") Long productId,
            @PathVariable("cartId") Long cartId,
            @PathVariable("quantity") Integer quantity
    ){
        cartService.updateProductQuantityInCart(productId, cartId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{cartId}/cart/{productId}")
    public ResponseEntity<String> deleteProductFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("productId") Long productId
    ){
        String status = cartService.deleteProductFromCart(cartId, productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
