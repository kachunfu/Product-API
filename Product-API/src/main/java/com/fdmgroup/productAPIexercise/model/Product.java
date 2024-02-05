package com.fdmgroup.productAPIexercise.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long productId;
	
	@NotBlank(message = "Name is a required field.")
	@Size(min = 2, max = 100 ,message = "Name must be between 2 to 100 characters long.")
	@Column(nullable = false, name = "product_name")  //database table level
	private String name;
	
	@Column(nullable = false)
	private BigDecimal price;
	
}
