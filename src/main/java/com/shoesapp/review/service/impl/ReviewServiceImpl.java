package com.shoesapp.review.service.impl;

import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.product.entity.Product;
import com.shoesapp.product.repository.ProductRepository;
import com.shoesapp.review.dto.ReviewDTO;
import com.shoesapp.review.entity.Review;
import com.shoesapp.review.repository.ReviewRepository;
import com.shoesapp.review.service.ReviewService;
import com.shoesapp.user.entity.User;
import com.shoesapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper mapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<ReviewDTO> getReviews(PageRequest pageRequest) {
        Page<Review> reviewDTOS = reviewRepository.findAll(pageRequest);
        return reviewDTOS.map(review -> mapper.map(review, ReviewDTO.class));
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO, Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id: " + userId + " not found!"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with Id: " + productId + " not found!"));

        Review review = new Review();
        review.setReviewDate(LocalDate.now());
        review.setBody(reviewDTO.getBody());
        review.setRate(review.getRate());
        review.setUser(user);
        review.setProduct(product);

        reviewRepository.save(review);

        return mapper.map(review, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream().map(review -> mapper.map(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream().map(review -> mapper.map(review, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public String deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with Id: " + reviewId + " not found!"));

        reviewRepository.delete(review);
        return "Review with Id: " + reviewId + " successfully deleted!";
    }


}
