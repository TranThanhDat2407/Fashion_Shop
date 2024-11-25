package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.OrderDTO;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderDTO> createOrUpdateOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        OrderDTO orderDTO = orderService.convertToDTO(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);

    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO updateDTO) {
        try {
            OrderDTO updatedOrder = orderService.updateOrder(id, updateDTO);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // Xử lý lỗi nếu Order không tồn tại
        }
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (orderOpt.isPresent()) {
            OrderDTO orderDTO = orderService.convertToDTO(orderOpt.get());
            return ResponseEntity.ok(orderDTO);
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }


}
