package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "Roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
}
