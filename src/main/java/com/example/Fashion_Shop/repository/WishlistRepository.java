package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT w FROM Wishlist w " +
            "WHERE w.user.id = :userId")
    Page<Wishlist> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT w FROM Wishlist w " +
            "WHERE w.user.id = :userId AND w.sku.id = :skuId")
    Optional<Wishlist> findByUserIdAndSKU(@Param("userId") Long userId, Long skuId);

    @Modifying
    @Query("DELETE FROM Wishlist w WHERE w.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
