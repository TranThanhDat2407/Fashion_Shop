package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT pi.imageUrl FROM ProductImage pi WHERE pi.product.id = :productId AND pi.color.id = :colorId")
    Optional<String> findImageByProductAndColor(@Param("productId") Long productId, @Param("colorId") Long colorId);
    @Query("SELECT pi FROM ProductImage pi " +
            "WHERE pi.color.id = :colorId AND pi.product.id = :productId")
    ProductImage findByColorIdAndProductId(@Param("colorId") Long colorId,@Param("productId") Long productId);
}
