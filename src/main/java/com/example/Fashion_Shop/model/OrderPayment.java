package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "transaction_id", length = 100)
    private String transactionId;

    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @Column(name = "payment_gateway_response", columnDefinition = "NVARCHAR(MAX)")
    private String paymentGatewayResponse;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", length = 20)
    private String status;



}
