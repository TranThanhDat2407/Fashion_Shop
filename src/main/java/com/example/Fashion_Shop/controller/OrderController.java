package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.dto.OrderDTO;
import com.example.Fashion_Shop.dto.OrderStatus;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.service.orders.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {


    private final OrderService orderService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(orders);
    }


    @PostMapping
    public ResponseEntity<OrderDTO> createOrUpdateOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        OrderDTO orderDTO = orderService.convertToDTO(savedOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PostMapping("/create-from-cart/{userId}")
    public ResponseEntity<OrderDTO> createOrderFromCart(@PathVariable Long userId) {

        Order savedOrder = orderService.createOrderFromCart(userId);

        OrderDTO orderDTO = orderService.convertToDTO(savedOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO updateDTO) {
//        try {
//            OrderDTO updatedOrder = orderService.updateOrder(id, updateDTO);
//            return ResponseEntity.ok(updatedOrder);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
