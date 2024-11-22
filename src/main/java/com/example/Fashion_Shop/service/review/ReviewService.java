package com.example.Fashion_Shop.service.review;

import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.Review;
import com.example.Fashion_Shop.repository.ReviewRepository;
import com.example.Fashion_Shop.response.review.ReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    public Page<ReviewResponse> getReviewsByProduct(Long productId, int page, int size, String sortBy, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviews = reviewRepository.findByProduct(productId, pageable);

        return reviews.map(ReviewResponse::fromReview);
    }

}
