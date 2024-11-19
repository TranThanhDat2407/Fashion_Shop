package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="image_url")
    private String imageUrl;

    @Column(name = "is_thumbnail")
    private boolean isThumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_value_id")
    private AttributeValue color;
}
