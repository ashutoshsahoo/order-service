package com.ashu.order.service;

import com.ashu.order.model.CreateOrderInput;
import com.ashu.order.model.Order;

public interface OrderService {

	Order create(CreateOrderInput createOrderInput);
}
