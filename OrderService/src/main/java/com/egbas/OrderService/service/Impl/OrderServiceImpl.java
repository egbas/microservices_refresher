package com.egbas.OrderService.service.Impl;

import com.egbas.OrderService.dto.OrderRequest;
import com.egbas.OrderService.entity.Order;
import com.egbas.OrderService.external.client.PaymentService;
import com.egbas.OrderService.external.client.ProductService;
import com.egbas.OrderService.external.request.PaymentRequest;
import com.egbas.OrderService.external.request.PaymentResponse;
import com.egbas.OrderService.external.request.ProductResponse;
import com.egbas.OrderService.payload.OrderResponse;
import com.egbas.OrderService.repository.OrderRepository;
import com.egbas.OrderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("Reducing quantity before placing order... ");

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("placing new order! ");
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus("CREATED")
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment...");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully, Changing order status.");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occured in payment, changing order status");
            orderStatus = "PAYMENT FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order successfully placed!");



        return order.getOrderId();
    }

    @Override
    public OrderResponse getOrderById(long id) {

        log.info("Getting order by Id...");
        Order order = orderRepository.findById(id).get();

        log.info("Invoking product service to fetch the product ID...");

        ProductResponse productResponse = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class
        );

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails
                .builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        log.info("Getting payment information from payment service...");

        PaymentResponse paymentResponse = restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/orders/"+ order.getOrderId(), PaymentResponse.class
        );

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails
                .builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .status(paymentResponse.getStatus())
                .build();


        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.getOrderId())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();

        log.info("Returning order with order ID: {}", orderResponse.getOrderId());
        return orderResponse;
    }
}
