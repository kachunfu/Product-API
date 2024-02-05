package com.fdmgroup.productAPIexercise.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.productAPIexercise.exception.ProductNotFoundException;
import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	public ProductController(ProductService productService) {
		this.productService = productService;
	}


	private ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> saveProduct(@Valid @RequestBody Product product, BindingResult bindingResult ){
		
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.productService.saveProduct(product), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<?> findAllProducts(){
		return new ResponseEntity<>(this.productService.findAllProducts(), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> updateProduct(@RequestBody Product product){
		if (product == null) {
			return new ResponseEntity<>(
					new ProductNotFoundException("The product is not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(this.productService.updateProduct(product), HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId) {
		Product product = this.productService.getProductById(productId);
		
		if (product == null) {
			return new ResponseEntity<>(
					new ProductNotFoundException("The product with id: " + productId + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping("/find-by-name/{name}")
	public ResponseEntity<?> getProductByName(@PathVariable String name) {
		Product product = this.productService.getProductByName(name);
		if (product == null) {
			return new ResponseEntity<>(
					new ProductNotFoundException("The product with name: " + name + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
		Product product = this.productService.getProductById(productId);
		
		if (product == null) {
			return new ResponseEntity<>(
					new ProductNotFoundException("The product with id: " + productId + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		this.productService.deleteProductById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
}
