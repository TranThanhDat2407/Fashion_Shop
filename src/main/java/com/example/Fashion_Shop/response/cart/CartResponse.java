package com.example.Fashion_Shop.response.cart;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String message;
    private int totalPages;
    private List<CartItemResponse> cartItem;
}
