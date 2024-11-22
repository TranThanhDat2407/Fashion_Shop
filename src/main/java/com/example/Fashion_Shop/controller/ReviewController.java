package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.model.Review;

import com.example.Fashion_Shop.repository.ReviewRepository;
import com.example.Fashion_Shop.response.product.ProductResponse;
import com.example.Fashion_Shop.response.review.ReviewListResponse;
import com.example.Fashion_Shop.response.review.ReviewResponse;
import com.example.Fashion_Shop.service.review.ReviewService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ReviewListResponse> getReviewsByProduct(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
            ) {
        Page<ReviewResponse> reviews = reviewService.getReviewsByProduct(productId, page, size,sortBy, sortDirection);

        ReviewListResponse reviewListResponse = ReviewListResponse.builder()
                .reviews(reviews.getContent())
                .totalPages(reviews.getTotalPages())
                .build();

        return ResponseEntity.ok(reviewListResponse);
    }
}
