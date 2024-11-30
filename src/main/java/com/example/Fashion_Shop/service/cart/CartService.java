package com.example.Fashion_Shop.service.cart;

import com.example.Fashion_Shop.dto.CartItemDTO;
import com.example.Fashion_Shop.model.Cart;
import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.CartRepository;
import com.example.Fashion_Shop.repository.SkuRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.response.cart.CartItemResponse;
import com.example.Fashion_Shop.response.cart.CartResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final SkuRepository skuRepository;
    private final UserRepository userRepository;


    public Page<CartItemResponse> getUserCart(Long userId, int page, int size, String sortBy, String sortDirection) {
        // Xác định hướng sắp xếp
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Gọi repository để phân trang và sắp xếp
        Page<Cart> cartPage = cartRepository.findByUserId(userId, pageable);

        return cartPage.map(CartItemResponse::fromCart);
    }

    @Transactional
    public CartItemResponse addToCart(Long userId, CartItemDTO cartItemDTO) {
        // Kiểm tra SKU tồn tại
        SKU sku = skuRepository.findById(cartItemDTO.getSkuId())
                .orElseThrow(() -> new RuntimeException("SKU not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        // Tìm mục giỏ hàng nếu đã tồn tại
        Cart cartItem = cartRepository.findByUserIdAndSKU(userId, cartItemDTO.getSkuId())
                .orElse(Cart.builder()
                        .user(user)
                        .sku(sku)
                        .quantity(0)
                        .build());

        // Cập nhật số lượng
        int newQuantity = cartItem.getQuantity() + cartItemDTO.getQuantity();
        if (newQuantity > sku.getQtyInStock()) {
            newQuantity = sku.getQtyInStock();
        }
        cartItem.setQuantity(newQuantity);
        Cart savedCart = cartRepository.save(cartItem);

        return CartItemResponse.fromCart(savedCart);
    }

    public Integer countCartItem(Long userId){
        return cartRepository.sumQuantityByUserId(userId);
    }

    @Transactional
    public void deleteCartItem(Long cartId) {
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
        }
    }

    @Transactional
    public void deleteAllCart(Long userId) {
        cartRepository.deleteAllByUserId(userId);
    }

    @Transactional
    public CartItemResponse updateQuantity(Long cartId, Integer newQuantity) {
        Cart cartItem = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item with ID " + cartId + " not found."));

        if (newQuantity <= 0) {
            cartRepository.delete(cartItem);
            return null;
        } else {
            if(newQuantity > cartItem.getSku().getQtyInStock()) {
                cartItem.setQuantity(cartItem.getSku().getQtyInStock());
            }else{
                cartItem.setQuantity(newQuantity);
            }
             cartRepository.save(cartItem);
            return CartItemResponse.fromCart(cartItem);
        }
    }


    public List<CartItemResponse> getGuestCartItems(List<CartItemDTO> cartItems) {
        return cartItems.stream()
                .map(item -> {
                    SKU sku = skuRepository.findById(item.getSkuId())
                            .orElseThrow(() -> new RuntimeException("SKU not found"));
                    Product product = sku.getProduct();

                    // Sử dụng fromGuestCart để tạo CartItemResponse
                    return CartItemResponse.fromGuestCart(sku, product, item.getQuantity());
                })
                .toList();
    }

}
