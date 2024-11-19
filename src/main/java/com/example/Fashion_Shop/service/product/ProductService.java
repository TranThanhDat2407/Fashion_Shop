package com.example.Fashion_Shop.service.product;

import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.repository.ProductRepository;
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

    public Page<Product> getProducts(Long categoryId, int page, int size, String sortBy, String sortDirection) {
        // Xác định Sort Direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Tạo Pageable với Sort linh hoạt
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Gọi repository để lấy dữ liệu
        return productRepository.findAllWithVariantsAndCategory(categoryId, pageable);
    }

}
