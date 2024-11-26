package com.example.Fashion_Shop.configuration;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class VNPAYConfig {
    private static final String vnp_TmnCode = "IQUTYPIQ";
    private static final String vnp_HashSecret = "HJF2G7EHCHPX0K446LBH17FKQUF56MB5";
    private static final String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String vnp_ReturnUrl = "http://localhost:8080/api/payments/vnpay_return";
    private static final String vnp_CancelUrl = "http://localhost:8080/api/payments/vnpay_cancel";
    private static final String vnp_IpnUrl = "http://localhost:8080/api/payments/vnpay_ipn";

    // Tạo URL thanh toán
    public String createPaymentUrl(Double amount, String orderId, String orderInfo) {
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", vnp_TmnCode);
        vnpParams.put("vnp_TxnRef", orderId);
        vnpParams.put("vnp_OrderInfo", orderInfo);
        vnpParams.put("vnp_Amount", String.format("%.0f", amount * 100));  // VNPay yêu cầu tiền tệ tính theo đơn vị tiền tệ (VND), nhân với 100
        vnpParams.put("vnp_Currency", "VND");
        vnpParams.put("vnp_BankCode", "VNPAY");
        vnpParams.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnpParams.put("vnp_CancelUrl", vnp_CancelUrl);
        vnpParams.put("vnp_IpnUrl", vnp_IpnUrl);

        // Xây dựng chuỗi query (không chứa hash)
        String queryString = buildQueryString(vnpParams);

        // Tạo SecureHash và thêm vào URL
        String secureHash = generateHashData(vnpParams);
        System.out.println("Tham số thanh toán: " + vnpParams);
        System.out.println("Query String: " + queryString);
        System.out.println("Secure Hash: " + secureHash);
        return vnp_Url + "?" + queryString + "&vnp_SecureHash=" + secureHash;
    }

    private String generateHashData(Map<String, String> vnpParams) {
        StringBuilder hashData = new StringBuilder();

        // Sắp xếp các tham số theo thứ tự bảng chữ cái và loại bỏ tham số vnp_SecureHash
        vnpParams.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("vnp_SecureHash"))  // Loại bỏ vnp_SecureHash
                .sorted(Map.Entry.comparingByKey())  // Sắp xếp theo thứ tự bảng chữ cái
                .forEach(entry -> {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }
                });

        // Xóa dấu & cuối cùng nếu có
        if (hashData.length() > 0) {
            hashData.deleteCharAt(hashData.length() - 1);  // Xóa ký tự '&' cuối cùng
        }

        // Tính toán HMAC SHA512
        return hmacSHA512(hashData.toString(), vnp_HashSecret);
    }

    private String hmacSHA512(String data, String key) {
        try {
            // Khởi tạo khóa bí mật và thuật toán HMAC-SHA512
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(result);  // Chuyển kết quả thành chuỗi hex
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Error generating HMAC SHA512 hash", e);
        }
    }

    // Chuyển byte array thành chuỗi hex
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    private String buildQueryString(Map<String, String> vnpParams) {
        StringBuilder queryString = new StringBuilder();
        vnpParams.entrySet().forEach(entry -> {
            try {
                queryString.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();  // Handle error appropriately
            }
        });
        // Xóa dấu & cuối cùng nếu có
        if (queryString.length() > 0) {
            queryString.setLength(queryString.length() - 1);  // Xóa dấu "&" cuối cùng
        }
        return queryString.toString();
    }
}
