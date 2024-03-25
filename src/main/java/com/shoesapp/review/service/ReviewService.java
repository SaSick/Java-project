package com.shoesapp.review.service;

import com.shoesapp.review.dto.ReviewDTO;
import com.shoesapp.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReviewService {
    Page<ReviewDTO> getReviews(PageRequest pageRequest);

    ReviewDTO createReview(ReviewDTO reviewDTO, Long userId, Long productId);

    List<ReviewDTO> getReviewsByProductId(Long productId);

    List<ReviewDTO> getReviewsByUserId(Long userId);

    String deleteReview(Long reviewId);
}
