package com.example.Fashion_Shop.dto;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Integer id;
    private String shippingAddress;
    private String phoneNumber;
    private BigDecimal totalMoney;
    private String status;
    private List<OrderDetailDTO> orderDetails;
}
