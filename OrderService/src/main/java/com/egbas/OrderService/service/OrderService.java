package com.egbas.OrderService.service;

import com.egbas.OrderService.dto.OrderRequest;
import com.egbas.OrderService.payload.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(long id);
}
