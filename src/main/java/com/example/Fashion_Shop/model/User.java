package com.example.Fashion_Shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone", unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private Role role;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_requested_time")
    private LocalDateTime otpRequestedTime;

    @Column(name = "is_active")
    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> review;
}
