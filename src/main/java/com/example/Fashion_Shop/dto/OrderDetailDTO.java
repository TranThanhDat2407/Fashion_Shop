package com.example.Fashion_Shop.dto;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    private Integer id;
    private Integer skuId;
    private Integer quantity;
    private Double price;
    private Double totalMoney;
}
