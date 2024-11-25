package com.example.Fashion_Shop.service.vnpay_service;

import com.example.Fashion_Shop.configuration.VNPAYConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VNPayService {
 private final VNPAYConfig vnpayConfig;

 @Autowired
    public VNPayService(VNPAYConfig vnpayConfig) {
     this.vnpayConfig = vnpayConfig;
 }

 public String createPayment(Double amout, String orderId, String orderInfo){
     return vnpayConfig.createPaymentUrl(amout, orderId, orderInfo);
 }

}
