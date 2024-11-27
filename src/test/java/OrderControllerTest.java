package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.OrderDTO;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.service.orders.OrderService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        // Reset mock objects if needed
    }

    // Test for getOrdersByUserId
    @Test
    public void testGetOrdersByUserIdSuccess() {
        Integer userId = 1;
        OrderDTO orderDTO = new OrderDTO();
        when(orderService.getOrdersByUserId(userId)).thenReturn(Collections.singletonList(orderDTO));

        ResponseEntity<List<OrderDTO>> response = orderController.getOrdersByUserId(userId);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().size(), 1);
    }

    @Test
    public void testGetOrdersByUserIdNoContent() {
        Integer userId = 1;
        when(orderService.getOrdersByUserId(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<OrderDTO>> response = orderController.getOrdersByUserId(userId);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 204); // No Content
        assertNull(response.getBody());
    }

    // Test for createOrUpdateOrder
    @Test
    public void testCreateOrUpdateOrderSuccess() {
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(orderService.saveOrder(order)).thenReturn(order);
        when(orderService.convertToDTO(order)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.createOrUpdateOrder(order);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 201); // Created
        assertNotNull(response.getBody());
        assertEquals(response.getBody(), orderDTO);
    }

    // Test for updateOrder
    @Test
    public void testUpdateOrderSuccess() {
        Integer orderId = 1;
        OrderDTO updateDTO = new OrderDTO();

        when(orderService.updateOrder(orderId, updateDTO)).thenReturn(updateDTO);

        ResponseEntity<OrderDTO> response = orderController.updateOrder(orderId, updateDTO);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200); // OK
        assertEquals(response.getBody(), updateDTO);
    }

    @Test
    public void testUpdateOrderNotFound() {
        Integer orderId = 1;
        OrderDTO updateDTO = new OrderDTO();

        when(orderService.updateOrder(orderId, updateDTO)).thenThrow(new RuntimeException("Order not found"));

        ResponseEntity<OrderDTO> response = orderController.updateOrder(orderId, updateDTO);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 404); // Not Found
    }

    // Test for getOrderById
    @Test
    public void testGetOrderByIdSuccess() {
        Integer orderId = 1;
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));
        when(orderService.convertToDTO(order)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200); // OK
        assertEquals(response.getBody(), orderDTO);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        Integer orderId = 1;

        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 404); // Not Found
    }

    // Test for deleteOrder
    @Test
    public void testDeleteOrderSuccess() {
        Integer orderId = 1;

        doNothing().when(orderService).deleteOrder(orderId);

        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200); // OK
    }

    @Test
    public void testDeleteOrderFailure() {
        Integer orderId = 1;

        doThrow(new RuntimeException("Order not found")).when(orderService).deleteOrder(orderId);

        try {
            orderController.deleteOrder(orderId);
            fail("Expected exception not thrown");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "Order not found");
        }

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}
