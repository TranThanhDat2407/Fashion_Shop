package com.example.Fashion_Shop.service.product;

import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.repository.ProductRepository;
import com.example.Fashion_Shop.response.product.ProductDetailResponse;
import com.example.Fashion_Shop.response.product.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<ProductResponse> getProducts(
            Long categoryId, String keyword, int page, int size, String sortBy, String sortDirection) {
        // code cũ
//        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
//                ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
//        Page<Product> products = productRepository.findAllWithVariantsAndCategory(categoryId, pageable);
        // pagerequest trả về size với page
        Pageable pageable = PageRequest.of(page, size);

        // dựa theo sortDirection asc hay desc mà chạy query sort
        // do price nằm ở SKU còn name nằm ở product
        Page<Product> products = "asc".equalsIgnoreCase(sortDirection)
                ? productRepository.findAllWithSortAndKeywordASC(categoryId,keyword, sortBy, pageable)
                : productRepository.findAllWithSortAndKeywordDESC(categoryId,keyword, sortBy, pageable);

        return products.map(ProductResponse::fromProduct);
    }

    public ProductDetailResponse getProductDetails(Long productId, Long colorId, Long sizeId) {
        // Lấy sản phẩm từ repository
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tìm SKU được chọn dựa trên colorId và sizeId
        SKU selectedSku = product.getSku().stream()
                .filter(sku ->
                        sku.getColor().getId().equals(colorId) &&
                        sku.getSize().getId().equals(sizeId))
                .findFirst()
                .orElse(null);

        if (selectedSku == null) {
            throw new RuntimeException("No SKU found matching the given colorId and sizeId");
        }

        // Tìm ảnh được chọn từ SKU
        ProductImage selectedImage = selectedSku != null
                ? product.getProductImages().stream()
                .filter(image -> image.getColor().getId().equals(colorId))
                .findFirst()
                .orElse(null)
                : null;

        // Xây dựng response bằng cách gọi fromProduct
        return ProductDetailResponse.fromProduct(product, selectedSku, selectedImage);
    }

}
