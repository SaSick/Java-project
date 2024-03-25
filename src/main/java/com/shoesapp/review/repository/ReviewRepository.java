package com.shoesapp.review.repository;

import com.shoesapp.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.product.productId = ?1 and r.reviewId = ?2")
    List<Review> findByProductId(Long productId);

    @Query("select r from Review r where r.user.userId = ?1 and r.reviewId = ?2")
    List<Review> findByUserId(Long userId);
}
