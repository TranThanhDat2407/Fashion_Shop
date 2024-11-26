package com.example.Fashion_Shop.service.payment;

import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.model.OrderPayment;
import com.example.Fashion_Shop.repository.OrderPaymentRepository;
import com.example.Fashion_Shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    // Xử lý thanh toán
//    public String processPayment(int orderId, BigDecimal amount) {
//        // Kiểm tra đơn hàng
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // Kiểm tra số tiền thanh toán
//        if (amount != order.getTotalMoney()) {
//            return "Số tiền thanh toán không chính xác";
//        }
//
//        // Lưu thông tin thanh toán vào bảng order_payments
//        OrderPayment orderPayment = new OrderPayment();
//        orderPayment.setOrderId(orderId);
//        orderPayment.setAmount(amount);
//        orderPayment.setStatus("success"); // Giả sử thanh toán thành công
//        orderPaymentRepository.save(orderPayment);
//
//        // Cập nhật trạng thái đơn hàng thành "processing"
//        order.setStatus("processing");
//        orderRepository.save(order);
//
//        return "Thanh toán thành công cho đơn hàng " + orderId;
//    }


    public String processPayment(int orderId, BigDecimal amount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        OrderPayment payment = new OrderPayment();
        payment.setOrder(order);
        payment.setAmount(amount);
        payment.setTransactionDate(new Date());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentGatewayResponse("Success");
        payment.setStatus("Completed");

        orderPaymentRepository.save(payment);
        return "Thanh toán thành công!";
    }


}
