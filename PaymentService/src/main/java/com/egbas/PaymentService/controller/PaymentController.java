package com.egbas.PaymentService.controller;

import com.egbas.PaymentService.dto.PaymentRequest;
import com.egbas.PaymentService.payload.PaymentResponse;
import com.egbas.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/new")
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        long paymentId = paymentService.doPayment(paymentRequest);

        return new ResponseEntity<>(paymentId, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable long id){
        PaymentResponse paymentResponse = paymentService.getPaymentByOrderId(id);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
