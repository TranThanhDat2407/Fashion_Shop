package com.example.Fashion_Shop.response.cart;

import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.response.BaseResponse;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.product_images.ProductImageResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse extends BaseResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String productImage;
    private SkuResponse skuResponse;

    public static CartItemResponse fromCart(Cart cart) {
        CartItemResponse cartItemResponse = CartItemResponse.builder()
                .id(cart.getId()) // ID của mục giỏ hàng
                .productId(cart.getSku().getProduct().getId()) // ID sản phẩm từ SKU
                .productName(cart.getSku().getProduct().getName()) // Tên sản phẩm từ SKU
                .price(cart.getSku().getSalePrice()) // Giá bán từ SKU
                .quantity(cart.getQuantity()) // Số lượng sản phẩm trong giỏ hàng
                .productImage(cart.getSku().getProduct().getProductImages().stream()
                        // lấy ảnh từ sku có product id và color id giống
                        .filter(image -> image.getColor().getId().equals(cart.getSku().getProduct().getId()) &&
                                        image.getColor().getId().equals(cart.getSku().getColor().getId()))
                        .findFirst()
                        .map(ProductImage::getImageUrl)
                        .orElse(null)) // Lấy ảnh đầu tiên của sản phẩm nếu có
                .skuResponse(SkuResponse.fromSKU(cart.getSku()))// Chuyển đổi SKU thành SkuResponse
                .build();
        cartItemResponse.setCreateAt(cart.getCreateAt());
        cartItemResponse.setUpdateAt(cart.getUpdateAt());
        return cartItemResponse;
    }

}
