package com.ashu.order.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTS")
@Data
@NoArgsConstructor
public class Product implements Serializable {

	private static final long serialVersionUID = 2401312062096053678L;

	@Id
	@Column(name = "ID", unique = true, updatable = false, nullable = false)
	private String id;

	private String name;

	private String description;

	private String price;

}
