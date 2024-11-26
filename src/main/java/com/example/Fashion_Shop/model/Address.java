package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="address")
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String ward;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
