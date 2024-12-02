package com.example.Fashion_Shop.response.wishlist;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private String message;
    private int totalPages;
    private List<WishlistItemResponse> wishlistItems;
}
