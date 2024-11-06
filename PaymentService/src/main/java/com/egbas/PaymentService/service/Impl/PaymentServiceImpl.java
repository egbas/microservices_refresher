package com.egbas.PaymentService.service.Impl;

import com.egbas.PaymentService.dto.PaymentRequest;
import com.egbas.PaymentService.entity.TransactionDetails;
import com.egbas.PaymentService.enums.PaymentMode;
import com.egbas.PaymentService.payload.PaymentResponse;
import com.egbas.PaymentService.repository.PaymentRepository;
import com.egbas.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details {}", paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .orderId(paymentRequest.getOrderId())
                .paymentDate(Instant.now())
                .amount(paymentRequest.getAmount())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .paymentStatus("SUCCESS")
                .build();

        paymentRepository.save(transactionDetails);
         log.info("Transaction completed with ID {}", transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(long orderId) {
        log.info("Getting payment details by order iD...");

        TransactionDetails transactionDetails = paymentRepository.findByOrderId(Long.valueOf(orderId));

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .amount(transactionDetails.getAmount())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .status(transactionDetails.getPaymentStatus())
                .build();

        return paymentResponse;
    }
}
