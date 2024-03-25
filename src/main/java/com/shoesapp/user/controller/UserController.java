package com.shoesapp.user.controller;

import com.shoesapp.user.dto.UserDTO;
import com.shoesapp.user.dto.UserResponse;
import com.shoesapp.user.entity.User;
import com.shoesapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public UserResponse<User> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        Page<User> pagedResponse = userService.getAllUsers(PageRequest.of(page, size));

        UserResponse<User> userResponse = new UserResponse<>();
        userResponse.setData(pagedResponse.getContent());
        userResponse.setTotal(pagedResponse.getTotalElements());

        return userResponse;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("userId") Long userId
    ){
        UserDTO userDTO = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.FOUND).body(userDTO);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserDTO userDTO
    ){
        userService.updateUser(userId, userDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteuser(
            @PathVariable("userId") Long userId
    ){
        String status = userService.deleteUserById(userId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
