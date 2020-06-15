package com.ashu.order.query;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.ashu.order.model.Product;
import com.ashu.order.service.ProductService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductQuery implements GraphQLQueryResolver {

	private final ProductService productService;

	@PreAuthorize("isAuthenticated()")
	public Product productById(String id) {
		return productService.viewById(id);
	}
}
