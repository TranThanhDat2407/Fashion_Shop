package com.example.Fashion_Shop.dto;
import java.math.BigDecimal;
import java.util.List;

import com.example.Fashion_Shop.model.OrderDetail;
import com.example.Fashion_Shop.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @JsonProperty("orderId")
    private int orderId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("total_money")
    private BigDecimal totalMoney;

    @JsonProperty("status")
    private String status;

    @JsonProperty("QR_code")
    private String qrCode;

    @JsonProperty("is_active")
    private Boolean isActive = false;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("shipping_method")
    private String shippingMethod;


    @JsonProperty("orderDetails")
    private List<OrderDetailDTO> orderDetails;

}
