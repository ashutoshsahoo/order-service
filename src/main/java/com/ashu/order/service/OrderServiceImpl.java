package com.ashu.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import com.ashu.order.exception.ResourceNotFoundException;
import com.ashu.order.model.CreateOrderInput;
import com.ashu.order.model.Customer;
import com.ashu.order.model.Order;
import com.ashu.order.model.Product;
import com.ashu.order.repository.CustomerRepository;
import com.ashu.order.repository.OrderRepository;
import com.ashu.order.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	private final CustomerRepository customerRepository;

	@Override
	public Order create(CreateOrderInput createOrderInput) {
		Product product = productRepository.findById(createOrderInput.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product", createOrderInput.getProductId()));
		Customer customer = customerRepository.findById(createOrderInput.getCustomerId())
				.orElseThrow(() -> new ResourceNotFoundException("Customer", createOrderInput.getCustomerId()));
		Order order = Order.builder().customer(customer).product(product).quantity(createOrderInput.getQuantity())
				.status("NEW").id(UUID.randomUUID().toString()).build();
		return orderRepository.save(order);
	}
}
