package com.shoesapp.user.service;


import com.shoesapp.user.dto.UserDTO;
import com.shoesapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {


    Page<User> getAllUsers(PageRequest pageRequest);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUserById(Long userId);
}
