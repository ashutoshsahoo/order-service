package com.ashu.order.service;

import com.ashu.order.model.Product;

public interface ProductService {

	Product create(Product product);

	Product viewById(String id);
}
