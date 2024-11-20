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
//    @Query("""
//    SELECT DISTINCT p FROM Product p
//     JOIN FETCH p.sku sku
//     JOIN FETCH sku.color c
//     JOIN FETCH sku.size s
//     WHERE (:categoryId IS NULL
//            OR p.category.id = :categoryId
//            OR p.category.parentCategory.id = :categoryId
//            OR p.category.parentCategory.id IN
//                (SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId))
//""")
//    Page<Product> findAllWithVariantsAndCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.sku s " +
            "WHERE (:categoryId IS NULL " +
            "      OR p.category.id = :categoryId " +
            "      OR p.category.parentCategory.id = :categoryId " +
            "      OR p.category.parentCategory.id IN " +
            "          (SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId)) " +
            "AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "GROUP BY p " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN p.name END ASC, " +
            "CASE WHEN :sortBy = 'price' THEN MIN(s.salePrice) END ASC, " +
            "CASE WHEN :sortBy = 'createAt' THEN p.createAt END ASC")
    Page<Product> findAllWithSortAndKeywordASC(@Param("categoryId") Long categoryId,
                                            @Param("keyword") String keyword,
                                            @Param("sortBy") String sortBy,
                                            Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN p.sku s " +
            "WHERE (:categoryId IS NULL " +
            "      OR p.category.id = :categoryId " +
            "      OR p.category.parentCategory.id = :categoryId " +
            "      OR p.category.parentCategory.id IN " +
            "          (SELECT c.id FROM Category c WHERE c.parentCategory.id = :categoryId)) " +
            "AND (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "GROUP BY p " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'name' THEN p.name END DESC, " +
            "CASE WHEN :sortBy = 'price' THEN MIN(s.salePrice) END DESC, " +
            "CASE WHEN :sortBy = 'createAt' THEN p.createAt END DESC")
    Page<Product> findAllWithSortAndKeywordDESC(@Param("categoryId") Long categoryId,
                                            @Param("keyword") String keyword,
                                            @Param("sortBy") String sortBy,
                                            Pageable pageable);
}
