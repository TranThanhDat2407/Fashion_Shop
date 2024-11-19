package com.example.Fashion_Shop.response.SKU;

import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.response.attribute_values.ColorResponse;
import com.example.Fashion_Shop.response.attribute_values.SizeResponse;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkuResponse {
    private Long id;
    private Integer qtyInStock;
    private Double originalPrice;
    private Double salePrice;
    private ColorResponse color;
    private SizeResponse size;

    public static SkuResponse fromSKU(SKU sku) {

        ColorResponse colorResponse = ColorResponse.fromColor(sku.getColor());
        SizeResponse sizeResponse = SizeResponse.fromSize(sku.getSize());

        return SkuResponse.builder()
                .id(sku.getId())
                .qtyInStock(sku.getQtyInStock())
                .originalPrice(sku.getOriginalPrice())
                .salePrice(sku.getSalePrice())
                .color(colorResponse)
                .size(sizeResponse)
                .build();
    }
}
