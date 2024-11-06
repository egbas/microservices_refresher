package com.egbas.OrderService.controller;

import com.egbas.OrderService.dto.OrderRequest;
import com.egbas.OrderService.payload.OrderResponse;
import com.egbas.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable long id){
        OrderResponse orderResponse = orderService.getOrderById(id);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
