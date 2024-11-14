package com.egbas.ProductService.repository;

import com.egbas.ProductService.entity.Product;
import com.egbas.ProductService.model.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
