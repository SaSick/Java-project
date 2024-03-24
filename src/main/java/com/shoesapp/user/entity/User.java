package com.shoesapp.user.entity;


import com.shoesapp.address.entity.Address;
import com.shoesapp.cart.entity.Cart;
import com.shoesapp.review.entity.Review;
import com.shoesapp.role.entity.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "address_id"))
    List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @MapKey(name = "reviewDate")
    private List<Review> reviewMap = new ArrayList<>();

}
