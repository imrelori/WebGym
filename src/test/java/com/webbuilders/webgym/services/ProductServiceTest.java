package com.webbuilders.webgym.services;

import com.webbuilders.webgym.converters.ProductCommandToProduct;
import com.webbuilders.webgym.converters.ProductToProductCommand;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    ProductServiceImpl productService;
    ProductCommandToProduct productCommandToProduct;
    ProductToProductCommand productToProductCommand;
    Product product;
    Long productId = 1L;

    @Mock
    ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository, productCommandToProduct, productToProductCommand);
        product = new Product();
    }

    @Test
    public void getProducts() throws Exception {

        HashSet productData = new HashSet();
        productData.add(product);

        when(productService.getProducts()).thenReturn(productData);

        Set<Product> products = productService.getProducts();

        assertEquals(products.size(), 1);
        assertTrue(products.contains(product));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void findProductById() throws Exception {

        product.setId(productId);
        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productService.findProductById(productId)).thenReturn(productOptional.get());
        Product result = productService.findProductById(productId);

        assertEquals(result, product);

        verify(productRepository, times(1)).findById(productId);
    }

    @Test(expected = RuntimeException.class)
    public void findProductByIdNoProduct() throws Exception {

        product.setId(2L);
        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productService.findProductById(productId)).thenReturn(productOptional.get());
        productService.findProductById(productId);

        verify(productRepository, times(1)).findById(productId);
    }
}
