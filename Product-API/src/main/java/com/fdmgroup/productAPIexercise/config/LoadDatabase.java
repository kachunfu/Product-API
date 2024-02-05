package com.fdmgroup.productAPIexercise.config;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.repository.ProductRepository;

@Configuration
public class LoadDatabase {
	
	private static final Logger log = Logger.getLogger(LoadDatabase.class.getName());

	  @Bean
	  CommandLineRunner initDatabase(ProductRepository productRepository) {

		  Product product = new Product(10L, "food", new BigDecimal(50));

		  Product product1 = new Product(11L, "drinks", new BigDecimal(20));
		  
		  Product product2 = new Product(12L, "car", new BigDecimal(34500));

		  Product product3 = new Product(13L, "bike", new BigDecimal(15630));



	    return args -> {
	      log.info("Preloading product " + productRepository.save(product));
	      log.info("Preloading product1 " + productRepository.save(product1));
	      log.info("Preloading product2 " + productRepository.save(product2));
	      log.info("Preloading product3 " + productRepository.save(product3));
	    };
	  }

}
