package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.service.orders.OrderService;
import com.example.Fashion_Shop.service.payment.PaymentService;
import com.example.Fashion_Shop.service.vnpay_service.VNPayService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final OrderService orderService;

    @PostMapping("/process/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable("orderId") int orderId) {
        try {
            // Truy xuất thông tin đơn hàng từ service
            Order order = orderService.getOrderById(orderId);
            BigDecimal amount = order.getTotalMoney();  // Lấy số tiền từ đơn hàng

            // Xử lý thanh toán với số tiền đã lấy được
            String paymentStatus = paymentService.processPayment(orderId, amount);
            return ResponseEntity.ok(paymentStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi khi xử lý thanh toán");
        }
    }








//
//    @Autowired
//    private VNPayService vnPayService;
//
//    // Tạo URL thanh toán VNPay
//    @PostMapping("/create")
//    public ResponseEntity<String> createPayment(
//            @RequestParam("amount") Double amount,
//            @RequestParam("orderId") String orderId,
//            @RequestParam("orderInfo") String orderInfo) {
//        try {
//            String paymentUrl = vnPayService.createPayment(amount, orderId, orderInfo);
//            return ResponseEntity.ok(paymentUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error creating payment URL");
//        }
//    }
//
//    // URL nhận kết quả thanh toán từ VNPay (nếu thanh toán thành công hoặc thất bại)
//    @GetMapping("/vnpay_return")
//    public ResponseEntity<Map<String, String>> handleVNPAYReturn(@RequestParam Map<String, String> params) {
//        String responseCode = params.get("vnp_ResponseCode");
//        Map<String, String> response = new HashMap<>();
//        if ("00".equals(responseCode)) {
//            response.put("status", "success");
//            response.put("message", "Payment successful");
//        } else {
//            response.put("status", "fail");
//            response.put("message", "Payment failed");
//        }
//        return ResponseEntity.ok(response);
//    }
//
//    // URL nhận thông báo IPN từ VNPay
//    @PostMapping("/vnpay_ipn")
//    public ResponseEntity<String> handleVNPAYIPN(@RequestParam Map<String, String> params) {
//        try {
//            String responseCode = params.get("vnp_ResponseCode");
//            String txnRef = params.get("vnp_TxnRef");
//            String amount = params.get("vnp_Amount");
//            String secureHash = params.get("vnp_SecureHash");
//
//            // Kiểm tra secure hash để xác minh tính toàn vẹn của dữ liệu
//            boolean isValid = vnPayService.validateSecureHash(params, secureHash);
//            if (!isValid) {
//                return ResponseEntity.status(400).body("Invalid SecureHash");
//            }
//
//            if ("00".equals(responseCode)) {
//                // Thanh toán thành công
//                return ResponseEntity.ok("Thanh toán thành công. Mã giao dịch: " + txnRef);
//            } else {
//                // Thanh toán thất bại
//                return ResponseEntity.status(500).body("Thanh toán thất bại. Mã lỗi: " + responseCode);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error processing IPN: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/vnpay_cancel")
//    public ResponseEntity<String> handleVNPAYCancel() {
//        return ResponseEntity.status(500).body("Payment cancelled");
//    }





}
