package com.egbas.OrderService.dto;

import com.egbas.OrderService.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private long productId;
    private long quantity;
    private long totalAmount;
    private PaymentMode paymentMode;
    private Instant orderDate;
    private String orderStatus;
}
