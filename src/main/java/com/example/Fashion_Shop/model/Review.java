package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "Reviews")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skus_id")
    private SKU sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @Column(name = "comment")
    private String comment;
}
