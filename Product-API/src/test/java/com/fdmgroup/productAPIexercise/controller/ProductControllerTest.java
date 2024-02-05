package com.fdmgroup.productAPIexercise.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.service.ProductService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;

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

		given(productService.saveProduct(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product)));

		// then - verify the result or output using assert statements

		//@formatter:off
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price", 
						is(product.getPrice().intValue())));
		// @formatter:on
	}
	
	// JUnit test for Get All products REST API
    @Test
    public void givenListOfProducts_whenFindAllProducts_thenReturnProductsList() throws Exception{
        // given - precondition or setup
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(Product.builder().name("car").price(new BigDecimal(34599)).build());
        listOfProducts.add(Product.builder().name("laptop").price(new BigDecimal(2699)).build());
        given(productService.findAllProducts()).willReturn(listOfProducts);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfProducts.size())));

    }
    
    // JUnit test for GET product by id REST API
    @Test
    public void givenProductId_whenGetProductById_thenReturnProductObject() throws Exception{
        // given - precondition or setup
        Long productId = 1L;
        Product product = Product.builder()
                .name("car")
                .price(new BigDecimal(2699))
                .build();
        given(productService.getProductById(productId)).willReturn(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/{productId}", productId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price", 
						is(product.getPrice().intValue())));
    }

    
    // negative scenario - valid product id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidProductId_whenGetProductById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
    	 Long productId = 1L;
        
        given(productService.getProductById(productId)).willReturn(null);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/{productId}", productId));

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
        given(productService.getProductByName(productName)).willReturn(product);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/products/find-by-name/{name}", productName));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", 
						is(product.getName())))
				.andExpect(jsonPath("$.price", 
						is(product.getPrice().intValue())));
    }
    
 // JUnit test for update product REST API - positive scenario
    @Test
    public void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdateProductObject() throws Exception{
        // given - precondition or setup

        Product updatedProduct = Product.builder()
                .name("gold")
                .price(new BigDecimal(99999))
                .build();
  
        given(productService.updateProduct(any(Product.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

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
    
 // JUnit test for delete product REST API
    @Test
    public void givenProductId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        Long productId = 1L;
        Product product = Product.builder()
                .name("car")
                .price(new BigDecimal(2699))
                .build();
       given(productService.getProductById(productId)).willReturn(product);
        
        willDoNothing().given(productService).deleteProductById(productId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/products/{productId}", productId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
    
    

}
