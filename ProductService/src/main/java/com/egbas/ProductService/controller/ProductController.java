package com.egbas.ProductService.controller;

import com.egbas.ProductService.model.ProductRequest;
import com.egbas.ProductService.model.ProductResponse;
import com.egbas.ProductService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest product) {

        Long productId = productService.addProduct(product);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);

    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {

        ProductResponse productResponse = productService.getProductById(productId);

            return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }

    @PutMapping("/reduceQuantity/{productId}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable Long productId, @RequestParam Long quantity){

        productService.reduceQuantity(productId, quantity);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
