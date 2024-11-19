package com.example.Fashion_Shop.response.attribute_values;

import com.example.Fashion_Shop.model.AttributeValue;
import com.example.Fashion_Shop.model.SKU;
import com.example.Fashion_Shop.response.SKU.SkuResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeResponse {
    private Long id;
    private String name;

    public static SizeResponse fromSize(AttributeValue size) {
        return SizeResponse.builder()
                .id(size.getId())
                .name(size.getValueName())
                .build();
    }
}
