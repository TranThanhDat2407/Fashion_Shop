package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Category;
import com.example.Fashion_Shop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    @Query("""
    SELECT DISTINCT p FROM Product p
     JOIN FETCH p.sku sku
     JOIN FETCH sku.color c
     JOIN FETCH sku.size s
     WHERE (:categoryId IS NULL 
            OR p.category.id = :categoryId 
            OR p.category.parentCategory.id = :categoryId 
            OR p.category.parentCategory.id IN 
                (SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId))
""")
    Page<Product> findAllWithVariantsAndCategory(@Param("categoryId") Long categoryId, Pageable pageable);

}
