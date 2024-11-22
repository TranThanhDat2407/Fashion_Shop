package com.example.Fashion_Shop.response.cart;

import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.response.BaseResponse;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalItemResponse {
    private Integer totalItem;
}
