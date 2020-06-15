package com.ashu.order.mutation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.ashu.order.model.Product;
import com.ashu.order.service.ProductService;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductMutation implements GraphQLMutationResolver {

	private final ProductService productService;

	@PreAuthorize("isAuthenticated()")
	public Product createProduct(Product product) {
		return productService.create(product);
	}
}
