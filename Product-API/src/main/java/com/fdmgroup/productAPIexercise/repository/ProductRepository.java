package com.fdmgroup.productAPIexercise.repository;

import org.springframework.data.repository.CrudRepository;

import com.fdmgroup.productAPIexercise.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	Product findByName(String name);

}
