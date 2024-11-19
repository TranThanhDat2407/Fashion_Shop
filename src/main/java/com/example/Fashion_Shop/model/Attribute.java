package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="attribute_name")
    private String attributeName;
}
