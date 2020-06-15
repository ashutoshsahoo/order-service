package com.ashu.order.service;

import org.springframework.stereotype.Service;

import com.ashu.order.exception.ResourceNotFoundException;
import com.ashu.order.model.Product;
import com.ashu.order.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public Product create(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public Product viewById(String id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
	}

}
