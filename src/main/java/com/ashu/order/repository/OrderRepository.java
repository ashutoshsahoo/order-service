package com.ashu.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
