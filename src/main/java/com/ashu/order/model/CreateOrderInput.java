package com.ashu.order.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderInput implements Serializable {

	private static final long serialVersionUID = -4535593364158100584L;

	private String customerId;

	private String productId;

	private int quantity;
}
