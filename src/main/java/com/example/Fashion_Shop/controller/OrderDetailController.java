package com.example.Fashion_Shop.controller;
import com.example.Fashion_Shop.dto.OrderDetailDTO;
import com.example.Fashion_Shop.model.OrderDetail;
import com.example.Fashion_Shop.service.order_detail.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;


    @PostMapping
    public ResponseEntity<OrderDetail>  createOrUpdateOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {

            OrderDetail savedOrderDetail = orderDetailService.saveOrderDetail(orderDetail);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetail);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(null);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.getAllOrderDetails();
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        return orderDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/order/{orderId}")
    public List<OrderDetailDTO> getOrderDetailsByOrderId(@PathVariable Integer orderId) {
        System.out.println("Fetching order details for orderId: " + orderId);
        return orderDetailService.getOrderDetailsByOrderId(orderId);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {

        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
