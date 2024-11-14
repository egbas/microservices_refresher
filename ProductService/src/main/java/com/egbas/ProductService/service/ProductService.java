package com.egbas.ProductService.service;

import com.egbas.ProductService.entity.Product;
import com.egbas.ProductService.model.ProductRequest;
import com.egbas.ProductService.model.ProductResponse;
import com.egbas.ProductService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long addProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        productRepository.save(product);

        return product.getProductId();

    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId).get();

        ProductResponse productResponse = ProductResponse.builder()
                .productId(product.getProductId())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productName(product.getProductName())
                .build();

        return productResponse;
    }

    public void reduceQuantity(Long productId, Long quantity) {

        Product product = productRepository.findById(productId).get();

        if (product.getQuantity() < quantity){
            throw new RuntimeException("Not enough products");
        }

        product.setQuantity(product.getQuantity() - quantity);

        productRepository.save(product);
    }
}
