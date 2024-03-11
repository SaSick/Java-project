package com.shoesapp.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse<T> {
    private List<T> data;
    private long total;

}
