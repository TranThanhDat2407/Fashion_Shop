package com.example.Fashion_Shop.service.wishlist;

import com.example.Fashion_Shop.dto.WishlistDTO;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.model.Wishlist;
import com.example.Fashion_Shop.repository.SkuRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.repository.WishlistRepository;
import com.example.Fashion_Shop.response.wishlist.WishlistItemResponse;
import com.example.Fashion_Shop.response.wishlist.WishlistResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WishlistService {
    WishlistRepository wishlistRepository;
    SkuRepository skuRepository;
    UserRepository userRepository;

    public Page<WishlistItemResponse> getUserWishlists(Long userId, int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Wishlist> wishlistPage = wishlistRepository.findByUserId(userId, pageable);

        return wishlistPage.map(WishlistItemResponse::fromWishlist);
    }

    @Transactional
    public WishlistItemResponse addToWishlist(Long userId, WishlistDTO wishlistDTO) {
        // Kiểm tra SKU tồn tại
        SKU sku = skuRepository.findById(wishlistDTO.getSkuId())
                .orElseThrow(() -> new RuntimeException("SKU not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Wishlist wishlist = wishlistRepository.findByUserIdAndSKU(userId, wishlistDTO.getSkuId())
                .orElse(Wishlist.builder()
                        .user(user)
                        .sku(sku)
                        .build());

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return WishlistItemResponse.fromWishlist(savedWishlist);
    }

    @Transactional
    public void deleteWishlistItem(Long wishlistId) {
        if(wishlistRepository.existsById(wishlistId)) {
            wishlistRepository.deleteById(wishlistId);
        }
    }

    @Transactional
    public void deleteAllWishlist(Long userId){
        wishlistRepository.deleteAllByUserId(userId);
    }

}
