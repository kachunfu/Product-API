package com.fdmgroup.productAPIexercise.service;

import org.springframework.stereotype.Service;

import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService {
	
	private ProductRepository productRepository;
	
	public ProductServiceImp(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product getProductById(Long productId) {
		return this.productRepository.findById(productId).orElse(null);
	}

	@Override
	public Product getProductByName(String name) {
		return this.productRepository.findByName(name);
	}

	@Override
	public Product saveProduct(Product product) {
		return this.productRepository.save(product);
	}
	
	@Override
	public Iterable<Product> findAllProducts(){
		return this.productRepository.findAll();
	}

	@Override
	public Product updateProduct(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	public void deleteProductById(Long productId) {
		this.productRepository.deleteById(productId);
	}

}
