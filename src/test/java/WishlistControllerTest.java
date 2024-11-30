package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.WishlistDTO;
import com.example.Fashion_Shop.response.wishlist.WishlistItemResponse;
import com.example.Fashion_Shop.response.wishlist.WishlistResponse;
import com.example.Fashion_Shop.service.wishlist.WishlistService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishlistService wishlistService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        // Clean up resources or reset mock objects if needed
    }

//    @Test
//    public void testGetUserWishlist() {
//        // Arrange
//        Long userId = 1L;
//        int page = 0, size = 5;
//        String sortBy = "id", sortDirection = "desc";
//
//        // Mocking a non-null Page object
//        WishlistItemResponse mockItem = new WishlistItemResponse();
//        Page<WishlistItemResponse> mockPage = new PageImpl<>(Collections.singletonList(mockItem)); // Simulate non-empty page
//        when(wishlistService.getUserWishlists(userId, page, size, sortBy, sortDirection)).thenReturn(mockPage);
//
//        // Act
//        ResponseEntity<WishlistResponse> response = wishlistController.getUserWishlist(userId, page, size, sortBy, sortDirection);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(response.getStatusCodeValue(), 200);
//        assertNotNull(response.getBody());
//        assertEquals(response.getBody().getMessage(), ""); // Message should be empty since there are items
//        assertEquals(response.getBody().getWishlistItems().size(), 1); // Ensure one item exists
//    }
//
//    @Test
//    public void testGetUserWishlistEmpty() {
//        // Arrange
//        Long userId = 1L;
//        int page = 0, size = 5;
//        String sortBy = "id", sortDirection = "desc";
//
//        // Mocking an empty Page object
//        Page<WishlistItemResponse> emptyPage = new PageImpl<>(Collections.emptyList());
//        when(wishlistService.getUserWishlists(userId, page, size, sortBy, sortDirection)).thenReturn(emptyPage);
//
//        // Act
//        ResponseEntity<WishlistResponse> response = wishlistController.getUserWishlist(userId, page, size, sortBy, sortDirection);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(response.getStatusCodeValue(), 200);
//        assertNotNull(response.getBody());
//        assertEquals(response.getBody().getMessage(), "Hiện không có sản phẩm nào trong danh sách yêu thích"); // Empty message
//        assertEquals(response.getBody().getWishlistItems().size(), 0); // No items in the list
//    }



    @Test
    public void testAddToWishlist() {
        Long userId = 1L;
        WishlistDTO wishlistDTO = new WishlistDTO();
        WishlistItemResponse wishlistItemResponse = new WishlistItemResponse();

        when(wishlistService.addToWishlist(userId, wishlistDTO)).thenReturn(wishlistItemResponse);

        ResponseEntity<WishlistItemResponse> response = wishlistController.addToWishlist(userId, wishlistDTO);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), wishlistItemResponse);
    }

    @Test
    public void testAddToWishlistFailure() {
        Long userId = 1L;
        WishlistDTO wishlistDTO = new WishlistDTO();

        // Mocking service to throw an exception
        when(wishlistService.addToWishlist(userId, wishlistDTO)).thenThrow(new RuntimeException("Failed to add to wishlist"));

        try {
            wishlistController.addToWishlist(userId, wishlistDTO);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Failed to add to wishlist");
        }

        //verify(wishlistService, times(1)).addToWishlist(userId, wishlistDTO);
    }


    @Test
    public void testDeleteWishlistItem() {
        Long wishlistId = 1L;

        doNothing().when(wishlistService).deleteWishlistItem(wishlistId);

        ResponseEntity<String> response = wishlistController.deleteWishlistItem(wishlistId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Delete successfully cart item: " + wishlistId);
    }

    @Test
    public void testDeleteWishlistItemFailure() {
        Long wishlistId = 1L;

        // Mocking service to throw an exception
        doThrow(new RuntimeException("Failed to delete wishlist item")).when(wishlistService).deleteWishlistItem(wishlistId);

        try {
            wishlistController.deleteWishlistItem(wishlistId);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Failed to delete wishlist item");
        }

//        verify(wishlistService, times(1)).deleteWishlistItem(wishlistId);
    }


    @Test
    public void testDeleteWishlistWithUserId() {
        Long userId = 1L;

        doNothing().when(wishlistService).deleteAllWishlist(userId);

        ResponseEntity<String> response = wishlistController.deleteWishlistWithUserId(userId);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Delete all cart item successfully user id: " + userId);
    }

    @Test
    public void testDeleteWishlistWithUserIdFailure() {
        Long userId = 1L;

        // Mocking service to throw an exception
        doThrow(new RuntimeException("Failed to delete all wishlist items")).when(wishlistService).deleteAllWishlist(userId);

        try {
            wishlistController.deleteWishlistWithUserId(userId);
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Failed to delete all wishlist items");
        }

//        verify(wishlistService, times(1)).deleteAllWishlist(userId);
    }

}
