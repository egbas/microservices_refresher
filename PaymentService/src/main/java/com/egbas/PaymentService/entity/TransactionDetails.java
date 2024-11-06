package com.egbas.PaymentService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
@Entity
@Table(name = "transaction_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long orderId;
    private String paymentMode;
    private String referenceNumber;
    private String paymentStatus;
    private Instant paymentDate;
    private long amount;
}
