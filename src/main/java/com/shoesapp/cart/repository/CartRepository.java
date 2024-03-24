package com.shoesapp.cart.repository;

import com.shoesapp.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.user.email = ?1 and c.cartId = ?2")
    Cart findCartByEmailAndCartId(String email, Long cartId);
}
