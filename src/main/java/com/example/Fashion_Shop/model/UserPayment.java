package com.example.Fashion_Shop.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "expiry_date", nullable = false)
    private java.sql.Date expiryDate;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "is_default")
    private Boolean isDefault;
}
