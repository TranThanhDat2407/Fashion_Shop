package com.example.Fashion_Shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SKUs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SKU extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="qty_in_stock")
    private Integer qtyInStock;

    @Column(name="original_price")
    private Double originalPrice;

    @Column(name="sale_price")
    private Double salePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    @JsonManagedReference
    private Product product;

    @ManyToOne
    @JoinColumn(name="color_value_id")
    private AttributeValue color;

    @ManyToOne
    @JoinColumn(name="size_value_id")
    private AttributeValue size;
}
