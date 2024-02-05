package com.fdmgroup.productAPIexercise.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.productAPIexercise.model.Product;

@DataJpaTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;

	
	private Product product;
	
	@BeforeEach
	public void setup() {
		
		product = Product.builder()
				.name("car")
				.price(new BigDecimal (34800))
				.build();
	}
	
    // JUnit test for save product operation
    @DisplayName("JUnit test for save product operation")
	@Test
	public void givenProductObject_whenSave_thenReturnSaveProduct() {
		
		//given - precondition or setup
		Product product = Product.builder()
				.name("laptop")
				.price(new BigDecimal (3800))
				.build();

        // when - action or the behaviour that we are going test
		Product savedProduct = productRepository.save(product);

        // then - verify the output
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getProductId()).isGreaterThan(0);
	}
	
    
    // JUnit test for get all products operation
    @DisplayName("JUnit test for save product operation")
    @Test
    public void givenProductList_whenFindAll_thenProductList() {
    	
    	Product product1= Product.builder()
    			.name("TV")
				.price(new BigDecimal (999))
				.build();
    	
    	productRepository.save(product);
    	productRepository.save(product1);
    	
    	//when 
    	List<Product> productList = (List<Product>) productRepository.findAll();
    	
    	   // then - verify the output
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
    }
    
 // JUnit test for get product by id operation
    @DisplayName("JUnit test for get product by id operation")
    @Test
    public void givenProductObject_whenFindById_thenReturnProductObject(){
        // given - precondition or setup
    	
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        Product productDB = productRepository.findById(product.getProductId()).get();

        // then - verify the output
        assertThat(productDB).isNotNull();
        assertThat(productDB.getName()).isEqualTo("car");
    }
    
    // JUnit test for get product by name operation
    @DisplayName("JUnit test for get product by name operation")
    @Test
    public void givenProductObject_whenFindByName_thenReturnProductObject(){
    	
        // given - precondition or setup
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        Product productDB = productRepository.findByName(product.getName());

        // then - verify the output
        assertThat(productDB).isNotNull();
        assertThat(productDB.getName()).isEqualTo("car");
    }
    
    // JUnit test for update product operation
    @DisplayName("JUnit test for update product operation")
    @Test
    public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct(){
    	
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        Product savedProduct = productRepository.findById(product.getProductId()).get();
        savedProduct.setName("mouse");
        savedProduct.setPrice(new BigDecimal(184));
        Product updatedProduct =  productRepository.save(savedProduct);

        // then - verify the output
        assertThat(updatedProduct.getName()).isEqualTo("mouse");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal(184));
    }
	
    // JUnit test for delete product operation
    @DisplayName("JUnit test for delete product operation")
    @Test
    public void givenProductObject_whenDelete_thenRemoveProduct(){

    	productRepository.save(product);

        // when -  action or the behaviour that we are going test
    	productRepository.deleteById(product.getProductId());
        Optional<Product> productOptional = productRepository.findById(product.getProductId());

        // then - verify the output
        assertThat(productOptional).isEmpty();
    }
				
	}

