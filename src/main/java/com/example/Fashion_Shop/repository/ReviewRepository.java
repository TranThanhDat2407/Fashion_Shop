package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "JOIN r.sku s " +
            "WHERE s.product.id = :productId")
    Page<Review> findByProduct(@Param("productId") Long productId, Pageable pageable);
}
