package com.example.Fashion_Shop.response.attribute_values;

import com.example.Fashion_Shop.model.AttributeValue;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorResponse {
    private Long id;
    private String name;
    private String value_img;

    public static ColorResponse fromColor(AttributeValue color) {
        return ColorResponse.builder()
                .id(color.getId())
                .name(color.getValueName())
                .value_img(color.getValueImg())
                .build();
    }
}
