package com.shoesapp.review.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewResponse<T> {
    private List<T> data;
    private long total;
}
