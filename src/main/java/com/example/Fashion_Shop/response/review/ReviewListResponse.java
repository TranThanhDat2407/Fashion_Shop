package com.example.Fashion_Shop.response.review;

import com.example.Fashion_Shop.response.product.ProductResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewListResponse {
    private List<ReviewResponse> reviews;
    private int totalPages;
}
