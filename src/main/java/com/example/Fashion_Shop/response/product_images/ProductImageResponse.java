package com.example.Fashion_Shop.response.product_images;

import com.example.Fashion_Shop.model.ProductImage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageResponse {
    private Long id;
    private String imageUrl;
    private boolean isThumbnail;
    private Long productId;
    private Long colorId;

    public static ProductImageResponse fromProductImage(ProductImage productImage) {
        return ProductImageResponse.builder()
                .id(productImage.getId())
                .imageUrl(productImage.getImageUrl())
                .isThumbnail(productImage.isThumbnail())
                .productId(productImage.getProduct().getId())
                .colorId(productImage.getColor().getId())
                .build();
    }
}
