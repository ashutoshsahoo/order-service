package com.ashu.order.query;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.ashu.order.model.Customer;
import com.ashu.order.service.CustomerService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerQuery implements GraphQLQueryResolver {

	private final CustomerService customerService;

	@PreAuthorize("hasAnyRole('ORDER_VIEWER','ORDER_ADMINISTRATOR')")
	public Customer customerById(String id) {
		return customerService.viewById(id);
	}
}
