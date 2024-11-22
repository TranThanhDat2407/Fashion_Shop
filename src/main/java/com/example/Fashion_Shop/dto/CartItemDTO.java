package com.example.Fashion_Shop.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Long skuId;
    private Integer quantity;
}
