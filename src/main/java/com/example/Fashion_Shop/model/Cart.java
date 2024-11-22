package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

import com.example.Fashion_Shop.model.User;

@Entity
@Table(name = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="sku_id")
    private SKU sku;

    @Column(name = "quantity")
    private Integer quantity;
}
