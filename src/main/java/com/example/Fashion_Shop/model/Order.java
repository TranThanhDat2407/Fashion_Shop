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
public class Order  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "total_money", nullable = false)
    private BigDecimal totalMoney;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "QR_code")
    private String qrCode;

    @Column(name = "is_active")
    private Boolean isActive = false;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "shipping_method")
    private String shippingMethod;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
