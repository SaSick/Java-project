package com.shoesapp.review.controller;

import com.shoesapp.review.dto.ReviewDTO;
import com.shoesapp.review.dto.ReviewResponse;
import com.shoesapp.review.entity.Review;
import com.shoesapp.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ReviewResponse<ReviewDTO> getAllReviews(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ){
        Page<ReviewDTO> pagedResponse = reviewService.getReviews(PageRequest.of(page, size));

        ReviewResponse<ReviewDTO> reviewResponse = new ReviewResponse<>();

        reviewResponse.setData(pagedResponse.getContent());
        reviewResponse.setTotal(pagedResponse.getTotalElements());

        return reviewResponse;
    }

    @PostMapping("/users/{userId}/product/{productId}/review")
    public ResponseEntity<ReviewDTO> addReview(
            @RequestBody ReviewDTO reviewDTO,
            @PathVariable("userId") Long userId,
            @PathVariable("productId") Long productId
    ){
        ReviewDTO review = reviewService.createReview(reviewDTO, userId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProduct(
            @PathVariable("productId") Long productId
    ){
        List<ReviewDTO> reviewDTOS = reviewService.getReviewsByProductId(productId);
        return new ResponseEntity<>(reviewDTOS, HttpStatus.FOUND);
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(
            @PathVariable("userId") Long userId
    ){
        List<ReviewDTO> reviewDTOS = reviewService.getReviewsByUserId(userId);
        return new ResponseEntity<>(reviewDTOS, HttpStatus.FOUND);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable("reviewId") Long reviewId
    ){
        String status = reviewService.deleteReview(reviewId);
        return  new ResponseEntity<>(status, HttpStatus.valueOf(204));
    }
}
