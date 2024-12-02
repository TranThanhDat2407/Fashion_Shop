package com.example.Fashion_Shop.response.wishlist;

import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.model.Wishlist;
import com.example.Fashion_Shop.response.BaseResponse;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.product_images.ProductImageResponse;
import com.example.Fashion_Shop.service.productImage.ProductImageService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class WishlistItemResponse {

    Long id;
    Long productId;
    String productName;
    Double price;
    String productImage;
    SkuResponse skuResponse;

    @Setter
    static ProductImageService productImageService; // Static để dùng chung trong toàn bộ ứng dụng

    public static WishlistItemResponse fromWishlist(Wishlist wishlist) {
        Long colorId = wishlist.getSku().getColor() != null ? wishlist.getSku().getColor().getId() : null;
        Long productId = wishlist.getSku().getProduct() != null ? wishlist.getSku().getProduct().getId() : null;
        log.info("Ma color: "+colorId+"Ma product: "+productId);
        WishlistItemResponse wishlistItemResponse = WishlistItemResponse.builder()
                .id(wishlist.getId())
                .productId(wishlist.getSku().getId())
                .productName(wishlist.getSku().getProduct().getName())
                .price(wishlist.getSku().getSalePrice())
                .productImage(productImageService.getProductImageByColorIdAndProductId(
                        colorId,
                        productId
                ))
                .skuResponse(SkuResponse.fromSKU(wishlist.getSku()))
                .build();
        return wishlistItemResponse;
    }
}
