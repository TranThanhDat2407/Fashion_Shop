package com.example.Fashion_Shop.response.cart;

import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.model.SKU;
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
                        .filter(image -> image.getProduct().getId().equals(cart.getSku().getProduct().getId()) &&
                                image.getColor().getId().equals(cart.getSku().getColor().getId()))
                        .findFirst()
                        .map(ProductImage::getImageUrl)
                        .orElse(null)) // Ensure null is returned if no match
                .skuResponse(SkuResponse.fromSKU(cart.getSku())) // Chuyển đổi SKU thành SkuResponse
                .build();

        cartItemResponse.setCreateAt(cart.getCreateAt());
        cartItemResponse.setUpdateAt(cart.getUpdateAt());

        return cartItemResponse;
    }

    public static CartItemResponse fromGuestCart(SKU sku, Product product, int quantity) {
        // Lấy hình ảnh sản phẩm dựa trên SKU (dựa vào productId và colorId)
        String productImageUrl = product.getProductImages().stream()
                .filter(image -> image.getProduct().getId().equals(product.getId()) &&
                        image.getColor().getId().equals(sku.getColor().getId()))
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse(null); // Trả về null nếu không tìm thấy

        return CartItemResponse.builder()
                .id(null) // Guest cart không có ID
                .productId(product.getId())
                .productName(product.getName())
                .price(sku.getSalePrice())
                .quantity(quantity)
                .productImage(productImageUrl) // Thêm hình ảnh
                .skuResponse(SkuResponse.fromSKU(sku))
                .build();
    }


}
