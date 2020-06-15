package com.ashu.order.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 9037554878198766168L;

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	private String id;

	private String name;

	private String email;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer", fetch = FetchType.EAGER)
	private Set<Order> orders = new HashSet<>();

	public void addOrder(Order order) {
		orders.add(order);
		order.setCustomer(this);
	}

	public void removeOrder(Order order) {
		orders.remove(order);
		order.setCustomer(null);
	}

}
