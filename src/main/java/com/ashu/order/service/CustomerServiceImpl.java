package com.ashu.order.service;

import org.springframework.stereotype.Service;

import com.ashu.order.exception.ResourceNotFoundException;
import com.ashu.order.model.Customer;
import com.ashu.order.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Override
	public Customer viewById(String id) {
		return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", id));
	}

}
