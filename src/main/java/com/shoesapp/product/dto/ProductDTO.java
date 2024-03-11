package com.shoesapp.product.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private String image;
    private String description;
    private Integer quantity;
    private double discount;
    private double price;
    private double specialPrice;
}
