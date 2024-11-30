package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.dto.CartItemDTO;
import com.example.Fashion_Shop.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c " +
            "WHERE c.user.id = :userId")
    Page<Cart> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Cart c " +
            "WHERE c.user.id = :userId AND c.sku.id = :skuId")
    Optional<Cart> findByUserIdAndSKU(@Param("userId") Long userId, Long skuId);

    @Query("SELECT SUM(c.quantity) FROM Cart c WHERE c.user.id = :userId")
    Integer sumQuantityByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
