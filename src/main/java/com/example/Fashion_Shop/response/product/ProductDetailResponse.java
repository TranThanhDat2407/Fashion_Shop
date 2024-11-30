package com.example.Fashion_Shop.response.product;

import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import com.example.Fashion_Shop.response.attribute_values.ColorResponse;
import com.example.Fashion_Shop.response.attribute_values.SizeResponse;
import com.example.Fashion_Shop.response.product_images.ProductImageResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String description;
    private SkuResponse selectedSku;
    private ProductImageResponse selectedImage;
    private List<SkuResponse> skus;
    private List<ProductImageResponse> productImages;

    public static ProductDetailResponse fromProduct(Product product, SKU selectedSku, ProductImage selectedImage) {
        List<SkuResponse> skuResponses = product.getSku().stream()
                .map(SkuResponse::fromSKU)
                .toList();

        List<ProductImageResponse> productImageResponses = product.getProductImages().stream()
                .map(ProductImageResponse::fromProductImage)
                .toList();

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .selectedSku(selectedSku != null ? SkuResponse.fromSKU(selectedSku) : null)
//                .selectedColor(ColorResponse.fromColor(selectedSku.getColor()))
//                .selectedSize(SizeResponse.fromSize(selectedSku.getSize()))
                .selectedImage(ProductImageResponse.fromProductImage(selectedImage))
                .skus(skuResponses)
                .productImages(productImageResponses)
                .build();
    }
}
