package com.shoesapp.review.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long reviewId;
    private String body;
    private int rate;
    private LocalDate reviewDate;
}
