package com.webbuilders.webgym.services;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.converters.*;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    ComponentCommandToComponent componentCommandToComponent;
    CategoryCommandToCategory categoryCommandToCategory;
    DetailCommandToDetail detailCommandToDetail;
    ProductCommandToProduct productCommandToProduct;

    ComponentToComponentCommand componentToComponentCommand;
    DetailToDetailCommand detailToDetailCommand;
    CategoryToCategoryCommand categoryToCategoryCommand;
    ProductToProductCommand productToProductCommand;

    ProductServiceImpl productService;
    Product product;

    Long productId = 1L;
    Long productIdFail = 2L;

    @Mock
    ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        componentCommandToComponent = new ComponentCommandToComponent();
        categoryCommandToCategory = new CategoryCommandToCategory();
        detailCommandToDetail = new DetailCommandToDetail(componentCommandToComponent);
        productCommandToProduct = new ProductCommandToProduct(categoryCommandToCategory, detailCommandToDetail);

        componentToComponentCommand = new ComponentToComponentCommand();
        detailToDetailCommand = new DetailToDetailCommand(componentToComponentCommand);
        categoryToCategoryCommand = new CategoryToCategoryCommand();
        productToProductCommand = new ProductToProductCommand(detailToDetailCommand, categoryToCategoryCommand);

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

        when(productRepository.findById(productId)).thenReturn(productOptional);

        Product returned = productService.findProductById(productId);

        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository);

        assertEquals(product, returned);
    }

    @Test(expected = RuntimeException.class)
    public void findProductByIdNoProduct() throws Exception {

        product.setId(productIdFail);
        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productRepository.findById(productId)).thenReturn(productOptional);

        productService.findProductById(productIdFail);
    }

    @Test
    public void findCommandById() {

        product.setId(productId);
        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productRepository.findById(productId)).thenReturn(productOptional);


        ProductCommand result =
                productToProductCommand.convert(productService.findProductById(productId));

        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository);

        assertEquals(result.getId(), productId);
    }

    @Test
    public void saveProductCommand() {

        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(productId);
        Product detachedProduct = productCommandToProduct.convert(productCommand);

        when(productRepository.save(detachedProduct)).thenReturn(detachedProduct);

        Product saveProduct = productRepository.save(detachedProduct);
        productCommand = productToProductCommand.convert(saveProduct);

        assertEquals(productCommand.getId(), productId);

        verify(productRepository, times(1)).save(detachedProduct);
        verifyNoMoreInteractions(productRepository);
    }
}
