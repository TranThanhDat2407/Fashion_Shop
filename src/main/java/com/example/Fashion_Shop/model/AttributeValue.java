package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attribute_values")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value_name", length = 100)
    private String valueName;

    @Column(name = "value_img", length = 100)
    private String valueImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "attribute_id")
    private Attribute attribute;
}
