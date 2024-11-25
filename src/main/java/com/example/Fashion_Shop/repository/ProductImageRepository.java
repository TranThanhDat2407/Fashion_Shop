package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT pi FROM ProductImage pi " +
            "WHERE pi.color.id = :colorId AND pi.product.id = :productId")
    ProductImage findByColorIdAndProductId(@Param("colorId") Long colorId,@Param("productId") Long productId);
}
