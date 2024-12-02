package com.example.Fashion_Shop.controller;


import com.example.Fashion_Shop.model.OrderPayment;
import com.example.Fashion_Shop.service.order_payment.OrderPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order-payments")
public class OrderPaymentController {
    @Autowired
    private OrderPaymentService orderPaymentService;


    @PostMapping
    public OrderPayment createOrUpdateOrderPayment(@RequestBody OrderPayment orderPayment) {
        return orderPaymentService.saveOrderPayment(orderPayment);
    }


    @GetMapping
    public List<OrderPayment> getAllOrderPayments() {
        return orderPaymentService.getAllOrderPayments();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderPayment> getOrderPaymentById(@PathVariable Long id) {
        Optional<OrderPayment> orderPayment = orderPaymentService.getOrderPaymentById(id);
        return orderPayment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderPayment(@PathVariable Long id) {
        orderPaymentService.deleteOrderPayment(id);
        return ResponseEntity.noContent().build();
    }
}
