package com.ashu.order;

import com.ashu.order.auth.dto.LoginRequest;
import com.ashu.order.auth.dto.SignupRequest;
import com.ashu.order.exception.ResourceNotFoundException;
import com.ashu.order.model.CreateOrderInput;
import com.ashu.order.model.Customer;
import com.ashu.order.model.Order;
import com.ashu.order.model.Product;
import com.ashu.order.service.CustomerService;
import com.ashu.order.service.OrderService;
import com.ashu.order.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
class OrderServiceApplicationTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private OrderService mockOrderService;

    @MockBean
    private ProductService mockProductService;

    @MockBean
    private CustomerService mockCustomerService;

    @BeforeAll
    public void init() throws IOException {
        SignupRequest request = OrderServiceTestUtils.createSignupRequest();
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("request", request);
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/signup.graphqls", variables);
        Assert.isTrue(response.isOk(), "User creation failed");

        LoginRequest loginRequest = LoginRequest.builder().username(request.getUsername())
                .password(request.getPassword()).build();
        variables = OrderServiceTestUtils.createObjectNode("request", loginRequest);
        response = graphQLTestTemplate.perform("graphqls/login.graphqls", variables);
        Assert.isTrue(response.isOk(), "User login failed");
        String token = response.get("$.data.login.token", String.class);
        Assert.notNull(token, "Token not found");
        graphQLTestTemplate.addHeader("Authorization", "Bearer " + token);
    }

    @Test
    void get_customerById_OK() throws IOException {
        Customer customer = OrderServiceTestUtils.createSampleCustomer();
        when(mockCustomerService.viewById(any(String.class))).thenReturn(customer);
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("id", "1");
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/customerById.graphqls", variables);
        assertNotNull(response);
        assertEquals(customer, response.get("$.data.customerById", Customer.class));
        assertEquals(0, response.getList("$.data.customerById.orders", Order.class).size());
    }

    @Test
    void get_customerByIdWithOrders_OK() throws IOException {
        Customer customer = OrderServiceTestUtils.createSampleCustomer();
        Product product = OrderServiceTestUtils.createSampleProdcut();
        Faker faker = new Faker();
        customer.addOrder(
                OrderServiceTestUtils.createSampleOrder(customer, product, faker.number().randomDigitNotZero()));
        when(mockCustomerService.viewById(any(String.class))).thenReturn(customer);
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("id", "1");
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/customerById.graphqls", variables);
        assertEquals(customer, response.get("$.data.customerById", Customer.class));
        assertEquals(1, response.getList("$.data.customerById.orders", Order.class).size());
    }

    @Test
    void get_customerById_NotFound() throws IOException {
        when(mockCustomerService.viewById(any(String.class))).thenThrow(new ResourceNotFoundException("Customer", "1"));
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("id", "1");
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/customerById.graphqls", variables);
        assertEquals("Customer not found for requested id=1", response.get("$.errors[0].message"));
        assertNull(response.get("$.data.customerById", Order.class));
    }

    @Test
    void create_product_OK() throws IOException {
        Product product = OrderServiceTestUtils.createSampleProdcut();
        when(mockProductService.create(any(Product.class))).thenReturn(product);
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("product", product);
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/createProduct.graphqls", variables);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals(product, response.get("$.data.createProduct", Product.class));
    }

    @Test
    void get_productById_OK() throws IOException {
        Product product = OrderServiceTestUtils.createSampleProdcut();
        when(mockProductService.viewById(any(String.class))).thenReturn(product);
        ObjectNode variables = OrderServiceTestUtils.createObjectNode("id", "1");
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/productById.graphqls", variables);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals(product, response.get("$.data.productById", Product.class));
    }

    @Test
    void create_order_OK() throws IOException {
        Product product = OrderServiceTestUtils.createSampleProdcut();
        Customer customer = OrderServiceTestUtils.createSampleCustomer();
        Faker faker = new Faker();
        CreateOrderInput orderInput = new CreateOrderInput();
        orderInput.setCustomerId(customer.getId());
        orderInput.setProductId(product.getId());
        orderInput.setQuantity(faker.number().randomDigitNotZero());
        Order order = OrderServiceTestUtils.createSampleOrder(customer, product, orderInput.getQuantity());

        when(mockCustomerService.viewById(any(String.class))).thenReturn(customer);
        when(mockProductService.viewById(any(String.class))).thenReturn(product);
        when(mockOrderService.create(any(CreateOrderInput.class))).thenReturn(order);

        ObjectNode variables = OrderServiceTestUtils.createObjectNode("order", orderInput);
        GraphQLResponse response = graphQLTestTemplate.perform("graphqls/createOrder.graphqls", variables);
        assertNotNull(response);
        assertTrue(response.isOk());
        assertEquals(order, response.get("$.data.createOrder", Order.class));
    }

}
