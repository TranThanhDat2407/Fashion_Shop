package com.example.Fashion_Shop.service.productImage;

import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    public String getProductImageByColorIdAndProductId(Long colorId, Long productId) {
        ProductImage productImage = productImageRepository.findByColorIdAndProductId(colorId, productId);
        return productImage.getImageUrl();
    }
}
