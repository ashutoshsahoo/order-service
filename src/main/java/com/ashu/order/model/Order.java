package com.ashu.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@Data
@EqualsAndHashCode(exclude = { "customer", "product" })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

	private static final long serialVersionUID = -1360683129814307223L;

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	private String id;

	private String status;

	private int quantity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.EAGER)
	private Product product;

}
