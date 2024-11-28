package com.example.Fashion_Shop.Payment;

import com.example.Fashion_Shop.controller.PaymentController;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.service.orders.OrderService;
import com.example.Fashion_Shop.service.payment.PaymentService;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class PaymentControllerTest {

    private PaymentController paymentController;

    private PaymentService paymentService;
    private OrderService orderService;

    @BeforeClass
    public void setUp() {
        paymentService = mock(PaymentService.class);
        orderService = mock(OrderService.class);
        paymentController = new PaymentController(paymentService, orderService);
    }

//    @Test
//    public void processPayment_SuccessfulPayment() throws Exception {
//        int orderId = 1;
//        Order order = new Order();
//        order.setTotalMoney(new BigDecimal("100.00"));
//
//        when(orderService.getOrderById(orderId)).thenReturn(order);
//        when(paymentService.processPayment(orderId, new BigDecimal("100.00"))).thenReturn("Payment Successful");
//
//        ResponseEntity<String> response = paymentController.processPayment(orderId);
//
//        assertEquals(response.getStatusCodeValue(), 200);
//        assertEquals(response.getBody(), "Payment Successful");
//        verify(orderService, times(1)).getOrderById(orderId);
//        verify(paymentService, times(1)).processPayment(orderId, new BigDecimal("100.00"));
//    }
}
