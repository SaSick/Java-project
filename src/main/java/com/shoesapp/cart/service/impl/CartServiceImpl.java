package com.shoesapp.cart.service.impl;

import com.shoesapp.cart.dto.CartDTO;
import com.shoesapp.cart.entity.Cart;
import com.shoesapp.cart.repository.CartRepository;
import com.shoesapp.cart.service.CartService;
import com.shoesapp.cartItem.entity.CartItem;
import com.shoesapp.cartItem.repository.CartItemRepository;
import com.shoesapp.exception.APIException;
import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
import com.shoesapp.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();

        if(carts.isEmpty()){
            throw new APIException("Carts not found!");
        }

        return carts.stream().
                map(cart -> {
                    CartDTO cartDTO = mapper.map(cart, CartDTO.class);
                    List<ProductDTO> products = cart.getCartItems().stream()
                            .map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();

                    cartDTO.setProductDTOS(products);
                    return cartDTO;
                }).toList();
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);

        if(cart == null){
            throw new ResourceNotFoundException("This cart not found!");
        }

        CartDTO cartDTO = mapper.map(cart, CartDTO.class);
        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();
        cartDTO.setProductDTOS(products);

        return cartDTO;
    }

    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id: " + cartId + " not found!" ));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found!"));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if(cartItem != null){
            throw new APIException("This product is already in cart!");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + " is not available!");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setQuantity(quantity);
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
        CartDTO cartDTO = mapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();
        cartDTO.setProductDTOS(productDTOS);
        return cartDTO;
    }

    @Override
    public void updateProductInCart(Long productId, Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id: " + cartId + " not found!" ));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found!"));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem == null){
            throw new ResourceNotFoundException("CartItem not found!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * cartItem.getQuantity()));

        cartItemRepository.save(cartItem);

    }

    @Override
    public CartDTO updateProductQuantityInCart(Long productId, Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id: " + cartId + " not found!" ));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found!"));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);

        if (cartItem == null){
            throw new ResourceNotFoundException("CartItem not found!");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + " is not available!");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

        cartItemRepository.save(cartItem);

        CartDTO cartDTO = mapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(p -> mapper.map(p.getProduct(), ProductDTO.class)).toList();

        cartDTO.setProductDTOS(productDTOS);
        return cartDTO;

    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id: " + cartId + " not found!" ));

        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);

        if(cartItem == null){
            throw new ResourceNotFoundException("CartItem not found!");
        }

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

        Product product = cartItem.getProduct();
        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);

        return "Product " + cartItem.getProduct().getProductName() + " removed from the cart!";
    }


}
