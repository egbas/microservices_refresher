package com.egbas.ProductService.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductRequest {
    private String productName;
    private Long price;
    private Long quantity;

}
