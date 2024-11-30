package com.example.Fashion_Shop.response.orders;

import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.cart.CartItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String orderId;
    private SkuResponse skuResponse;
    private String productName;
    private Double totalPrice;
    private String productImage;





}
