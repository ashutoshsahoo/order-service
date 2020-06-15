package com.ashu.order.mutation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.ashu.order.model.CreateOrderInput;
import com.ashu.order.model.Order;
import com.ashu.order.service.OrderService;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMutation implements GraphQLMutationResolver {

	private final OrderService orderService;

	@PreAuthorize("hasRole('ORDER_ADMINISTRATOR')")
	public Order createOrder(CreateOrderInput createOrderInput) {
		return orderService.create(createOrderInput);
	}
}
