package com.fdmgroup.productAPIexercise.integration;

import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		
		productRepository.deleteAll();
	}
	
	
	@Test
	public void givenProductObject_whenCreateProduct_thenReturnSavedProduct() throws Exception {

		// given - precondition or setup
		//@formatter:off
		Product product = Product.builder()
				.productId(1L)
				.name("car")
				.price(new BigDecimal(34800))
				.build();
		// @formatter:on
		
		//No Mocking is required


// when - action or behaviour that we are going test
		
		//@formatter:off
		ResultActions response = mockMvc.perform(post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)));
		// @formatter:on
		
// then - verify the result or output using assert statements

		//@formatter:off
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price", 
						is(product.getPrice().intValue())));
		// @formatter:on
	}
	
	// Integration test for Get All employees REST API
    @Test
    public void givenListOfProducts_whenFindAllProducts_thenReturnProductsList() throws Exception{
        // given - precondition or setup
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(Product.builder().name("car").price(new BigDecimal(34599)).build());
        listOfProducts.add(Product.builder().name("laptop").price(new BigDecimal(2699)).build());
        productRepository.saveAll(listOfProducts);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfProducts.size())));

    }
    
    // Integration test for GET product by id REST API
    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception{
        // given - precondition or setup
    	
        Product product = Product.builder()
                .name("car")
                .price(new BigDecimal(34500))
                .build();
        
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/{productId}", product.getProductId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price").value(34500.0));
    }
    
    // negative scenario - valid product id
    // Integration test for GET employee by id REST API
    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
    	 Long productId = 99999L;
        
    	 Product product = Product.builder()
                 .name("car")
                 .price(new BigDecimal(34500))
                 .build();
         
         productRepository.save(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/{id}", productId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    
    @Test
    public void givenProductName_whenGetProductByName_thenReturnProductObject() throws Exception{
        // given - precondition or setup
        String productName = "car";
        Product product = Product.builder()
                .name("car")
                .price(new BigDecimal(2699))
                .build();
        
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/find-by-name/{name}", productName));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price").value(2699));
						
    }
    
 // Integration test for update product REST API - positive scenario
    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception{
        // given - precondition or setup
    	
    	Product savedProduct = Product.builder()
                .name("car")
                .price(new BigDecimal(2699))
                .build();
    	
    	
        Product updatedProduct = Product.builder()
        		.productId(savedProduct.getProductId())
                .name("gold")
                .price(new BigDecimal(99999))
                .build();
        
        productRepository.save(savedProduct);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/products/")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedProduct)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", 
						is(updatedProduct.getName())))
				.andExpect(jsonPath("$.price", 
						is(updatedProduct.getPrice().intValue())));
    }
    
    //Integration test for delete product REST API
    @Test
    public void givenProductId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup

        Product product = Product.builder()
                .name("car")
                .price(new BigDecimal(2699))
                .build();
      
        productRepository.save(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/products/{productId}", product.getProductId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
    
    
	
}
