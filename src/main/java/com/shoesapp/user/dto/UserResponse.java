package com.shoesapp.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse<T> {
    private List<T> data;
    private long total;
}
