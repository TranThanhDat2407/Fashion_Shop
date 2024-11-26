package com.example.Fashion_Shop.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_money", nullable = false)
    private BigDecimal totalMoney;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "QR_code")
    private String qrCode;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
