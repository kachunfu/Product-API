package com.fdmgroup.productAPIexercise.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.productAPIexercise.model.Product;
import com.fdmgroup.productAPIexercise.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productService;

    private Product product;

    @BeforeEach
    public void setup(){

    	product = Product.builder()
    			.productId(1L)
				.name("car")
				.price(new BigDecimal (34800))
				.build();
    }
    
    // JUnit test for saveProduct method
    @DisplayName("JUnit test for saveProduct method")
    @Test
    public void givenProductObject_whenSaveProduct_thenReturnProductObject(){
        // given - precondition or setup

        given(productRepository.save(product)).willReturn(product);

        System.out.println(productRepository);
        System.out.println(productService);

        // when -  action or the behaviour that we are going test
        Product savedProduct = productService.saveProduct(product);

        System.out.println(savedProduct);
        // then - verify the output
        assertThat(savedProduct).isNotNull();
        verify(productRepository, times(1)).save(product);
    }
    
 // JUnit test for findAllProducts method
    @DisplayName("JUnit test for findAllProducts method")
    @Test
    public void givenProductsList_whenfindAllProducts_thenReturnProductsList(){
        // given - precondition or setup

    	Product product1 = Product.builder()
                .name("PS5")
                .price(new BigDecimal(799))
                .build();

        given(productRepository.findAll()).willReturn(List.of(product,product1));

        // when -  action or the behaviour that we are going test
        List<Product> productList = (List<Product>) productService.findAllProducts();

        // then - verify the output
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
        verify(productRepository, times(1)).findAll();
    }
    

    // JUnit test for findProductById method
    @DisplayName("JUnit test for getProductById method")
    @Test
    public void givenProductId_whenFindProductById_thenReturnProductObject(){
        // given
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        Product savedProduct = productService.getProductById(product.getProductId());

        // then
        assertThat(savedProduct).isNotNull();
        verify(productRepository, times(1)).findById(product.getProductId());
    }
    
 // JUnit test for findProductByName method
    @DisplayName("JUnit test for findProductByName method")
    @Test
    public void givenProductName_whenFindProductByName_thenReturnProductObject(){
        // given
        given(productRepository.findByName("car")).willReturn(product);

        // when
        Product savedProduct = productService.getProductByName(product.getName());

        // then
        assertThat(savedProduct).isNotNull();
        verify(productRepository, times(1)).findByName(product.getName());
    }
    
    
    // JUnit test for updateProduct method
    @DisplayName("JUnit test for updateProduct method")
    @Test
    public void givenProductObject_whenUpdateProduct_thenReturnUpdatedProduct(){
    	
        // given - precondition or setup
        given(productRepository.save(product)).willReturn(product);
        product.setName("lens");
        product.setPrice(new BigDecimal(4999));
        
        // when -  action or the behaviour that we are going test
        Product updatedProduct = productService.updateProduct(product);

        // then - verify the output
        assertThat(updatedProduct.getName()).isEqualTo("lens");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal(4999));
    }
    
    // JUnit test for deleteProduct method
    @DisplayName("JUnit test for deleteProduct method")
    @Test
    public void givenProductId_whenDeleteProduct_thenNothing(){
        // given - precondition or setup
        Long productId = 1L;

        willDoNothing().given(productRepository).deleteById(productId);

        // when -  action or the behaviour that we are going test
        productService.deleteProductById(productId);

        // then - verify the output
        verify(productRepository, times(1)).deleteById(productId);
    }
    
    
}
