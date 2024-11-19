package com.example.Fashion_Shop.response.product;

import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.response.BaseResponse;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.product_images.ProductImageResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    private Long id;
    private String name;
    private String description;
    private List<SkuResponse> skus;
    private List<ProductImageResponse> productImages;

    public static ProductResponse fromProduct(Product product) {
        List<SkuResponse> skuResponses = product.getSku().stream()
                .map(SkuResponse::fromSKU)
                .collect(Collectors.toList());

        List<ProductImageResponse> productImageResponses = product.getProductImages().stream()
                .map(ProductImageResponse::fromProductImage)
                .collect(Collectors.toList());

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .skus(skuResponses)
                .productImages(productImageResponses)
                .build();
        productResponse.setCreateAt(product.getCreateAt());
        productResponse.setUpdateAt(product.getUpdateAt());

        return productResponse;
    }

}

