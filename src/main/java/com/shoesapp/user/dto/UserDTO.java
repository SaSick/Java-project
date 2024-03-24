package com.shoesapp.user.dto;

import com.shoesapp.address.dto.AddressDTO;
import com.shoesapp.cart.dto.CartDTO;
import com.shoesapp.role.entity.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private AddressDTO addressDTO;
    private CartDTO cartDTO;
    private Set<Role> roles = new HashSet<>();

}
