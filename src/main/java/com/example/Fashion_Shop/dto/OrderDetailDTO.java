package com.example.Fashion_Shop.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("skuId")
    private Integer skuId;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("totalMoney")
    private Double totalMoney;

    public void setSkuId(Long id) {
    }
}
