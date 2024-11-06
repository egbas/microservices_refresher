package com.egbas.PaymentService.service;

import com.egbas.PaymentService.dto.PaymentRequest;
import com.egbas.PaymentService.payload.PaymentResponse;

public interface PaymentService {

    long doPayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentByOrderId(long id);
}
