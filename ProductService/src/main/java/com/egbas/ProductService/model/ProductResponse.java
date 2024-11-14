package com.egbas.ProductService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
}
