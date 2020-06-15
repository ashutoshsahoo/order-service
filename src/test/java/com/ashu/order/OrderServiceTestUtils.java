package com.ashu.order;

import java.util.Collections;
import java.util.UUID;

import com.ashu.order.auth.dto.SignupRequest;
import com.ashu.order.model.Customer;
import com.ashu.order.model.Order;
import com.ashu.order.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

public class OrderServiceTestUtils {

	public static SignupRequest createSignupRequest() {
		return SignupRequest.builder().username("USER").password("password@01").email("user@email.com")
				.roles(Collections.singleton("admin")).build();
	}

	public static ObjectNode createObjectNode(String field, Object object) {
		ObjectNode variables = new ObjectMapper().createObjectNode();
		variables.putPOJO(field, object);
		return variables;
	}

	public static Customer createSampleCustomer() {
		Faker faker = new Faker();
		Customer customer = new Customer();
		customer.setId(faker.number().digit());
		customer.setName(faker.name().name());
		customer.setEmail(faker.internet().emailAddress());
		return customer;
	}

	public static Product createSampleProdcut() {
		Faker faker = new Faker();
		Product product = new Product();
		product.setId(faker.number().digit());
		product.setName(faker.commerce().productName());
		product.setDescription(faker.lorem().sentence());
		product.setPrice(faker.commerce().price());
		return product;
	}

	public static Order createSampleOrder(Customer customer, Product product, int quntity) {
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setQuantity(quntity);
		order.setStatus("NEW");
		order.setCustomer(customer);
		order.setProduct(product);
		return order;
	}

}
