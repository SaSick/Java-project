package com.shoesapp.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse<T> {
    private List<T> data;
    private long total;
}
