package com.ashu.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.order.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
