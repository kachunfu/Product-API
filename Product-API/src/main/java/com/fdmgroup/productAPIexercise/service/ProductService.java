package com.fdmgroup.productAPIexercise.service;

import com.fdmgroup.productAPIexercise.model.Product;

public interface ProductService {

	Product getProductById(Long productId);
	
	Product getProductByName(String name);
	
	Product saveProduct(Product product);
	
	Product updateProduct(Product product);
	
	Iterable<Product>findAllProducts();
	
	void deleteProductById(Long productId);
	
	
	
}
