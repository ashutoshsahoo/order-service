package com.ashu.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.order.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
