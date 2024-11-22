package com.example.Fashion_Shop.response.review;

import com.example.Fashion_Shop.model.Review;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.response.BaseResponse;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.user.UserResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse extends BaseResponse {
    private Long id;
    private String comment;
    private Integer ratingValue;
    private SkuResponse sku;
    private UserResponse user;

    public static ReviewResponse fromReview(Review review) {
         ReviewResponse reviewResponse = ReviewResponse.builder()
                .id(review.getId())
                .comment(review.getComment())
                .ratingValue(review.getRatingValue())
                .sku(SkuResponse.fromSKU(review.getSku())) // Convert SKU to its response
                .user(UserResponse.fromUser(review.getUser())) // Convert User to its response
                .build();reviewResponse.setCreateAt(review.getCreateAt());
        reviewResponse.setUpdateAt(review.getUpdateAt());
        return reviewResponse;
    }

}
