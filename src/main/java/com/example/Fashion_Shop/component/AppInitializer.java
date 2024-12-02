package com.example.Fashion_Shop.component;

import com.example.Fashion_Shop.response.wishlist.WishlistItemResponse;
import com.example.Fashion_Shop.service.productImage.ProductImageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitializer implements CommandLineRunner {
    private final ProductImageService productImageService;

    public AppInitializer(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Override
    public void run(String... args) throws Exception {
        WishlistItemResponse.setProductImageService(productImageService);
    }
}
