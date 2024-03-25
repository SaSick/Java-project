package com.shoesapp.review.entity;

import com.shoesapp.product.entity.Product;
import com.shoesapp.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @NotBlank
    @Size(min = 6, message = "R description must contain atleast 6 characters")
    private String body;

    private int rate;
    private LocalDate reviewDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
