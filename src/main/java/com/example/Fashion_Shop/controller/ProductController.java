package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.model.Category;
import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.response.product.ProductListResponse;
import com.example.Fashion_Shop.response.product.ProductResponse;
import com.example.Fashion_Shop.service.product.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(required = false) Long categoryId, // Không bắt buộc
            @RequestParam(defaultValue = "") String keyword,  //keyword
            @RequestParam(defaultValue = "0") int page,  // Mặc định page là 0
            @RequestParam(defaultValue = "8") int size,     // Mặc định page size là 8
            @RequestParam(defaultValue = "createAt") String sortBy, // Mặc định sắp xếp theo ngày tạo product
            @RequestParam(defaultValue = "desc") String sortDirection // Mặc định sắp xếp giảm dần
    ) {
        try {
            Page<ProductResponse> productPage = productService.getProducts(categoryId, keyword, page, size, sortBy, sortDirection);

            ProductListResponse response = ProductListResponse.builder()
                    .products(productPage.getContent()) // Danh sách sản phẩm
                    .totalPages(productPage.getTotalPages()) // Tổng số trang
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
