package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.CartItemDTO;
import com.example.Fashion_Shop.dto.WishlistDTO;
import com.example.Fashion_Shop.response.cart.CartItemResponse;
import com.example.Fashion_Shop.response.cart.CartResponse;
import com.example.Fashion_Shop.response.cart.TotalItemResponse;
import com.example.Fashion_Shop.response.wishlist.TotalWishlistItemResponse;
import com.example.Fashion_Shop.response.wishlist.WishlistItemResponse;
import com.example.Fashion_Shop.response.wishlist.WishlistResponse;
import com.example.Fashion_Shop.service.wishlist.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/wishlist")
@AllArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<WishlistResponse> getUserWishlist(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {

        Page<WishlistItemResponse> wishlistPages = wishlistService.getUserWishlists(userId, page, size, sortBy, sortDirection);
        String message = wishlistPages.isEmpty() ? "Hiện không có sản phẩm nào trong danh sách yêu thích" : "";

        WishlistResponse wishlistResponse = WishlistResponse.builder()
                .wishlistItems(wishlistPages.getContent())
                .totalPages(wishlistPages.getTotalPages())
                .message(message)
                .build();

        return ResponseEntity.ok(wishlistResponse);
    }

    @PostMapping
    public ResponseEntity<WishlistItemResponse> addToWishlist(
            @RequestParam Long userId,
            @RequestBody WishlistDTO wishlistDTO
    ){
        return ResponseEntity.ok(wishlistService.addToWishlist(userId, wishlistDTO));
    }



    @DeleteMapping
    public ResponseEntity<String> deleteWishlistItem(
            @RequestParam Long wishlistId
    )
    {
        wishlistService.deleteWishlistItem(wishlistId);
        return ResponseEntity.ok("Delete successfully cart item: "+ wishlistId );
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> deleteWishlistWithUserId(
            @RequestParam Long userId
    )
    {
        wishlistService.deleteAllWishlist(userId);
        return ResponseEntity.ok("Delete all cart item successfully user id: "+ userId );
    }
}
