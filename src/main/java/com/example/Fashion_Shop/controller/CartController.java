package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.CartItemDTO;
import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.ProductImage;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.response.cart.CartItemResponse;
import com.example.Fashion_Shop.response.cart.CartResponse;
import com.example.Fashion_Shop.response.cart.TotalItemResponse;
import com.example.Fashion_Shop.service.cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;


    @GetMapping
    public ResponseEntity<CartResponse> getUserCart(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<CartItemResponse> cartPages = cartService.getUserCart(userId, page, size, sortBy, sortDirection);

        String message = cartPages.isEmpty() ? "Hiện không có sản phẩm nào trong giỏ hàng" : "";

        CartResponse cartResponse = CartResponse.builder()
                .cartItem(cartPages.getContent())
                .totalPages(cartPages.getTotalPages())
                .message(message)
                .build();

        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addToCart(
            @RequestParam Long userId,
            @RequestBody CartItemDTO cartItemDTO
            ){

        return ResponseEntity.ok(cartService.addToCart(userId, cartItemDTO));
    }

    @GetMapping("/totalItem")
    public ResponseEntity<TotalItemResponse> totalCartItem(
            @RequestParam Long userId
    ){
        TotalItemResponse total = TotalItemResponse.builder()
                .totalItem(cartService.countCartItem(userId))
                .build();

        return ResponseEntity.ok(total);
    }

    @PutMapping
    public ResponseEntity<CartItemResponse> updateCart(
            @RequestParam Long cartId,
            @RequestParam Integer newQuantity
    ){
        CartItemResponse cartItemResponse = cartService.updateQuantity(cartId, newQuantity);

        return ResponseEntity.ok(cartItemResponse);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCartItem(
            @RequestParam Long cartId
    )
    {
        cartService.deleteCartItem(cartId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<String> deleteCartWithUserId(
            @RequestParam Long userId
    )
    {
        cartService.deleteAllCart(userId);
        return ResponseEntity.ok("Delete all cart item successfully user id: "+ userId );
    }

    @PostMapping("/guest")
    public ResponseEntity<List<CartItemResponse>> getGuestCart(
            @RequestBody List<CartItemDTO> cartItems) {

        List<CartItemResponse> cartItemResponses = cartService.getGuestCartItems(cartItems);

        if (cartItemResponses.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }

        return ResponseEntity.ok(cartItemResponses);
    }

}
