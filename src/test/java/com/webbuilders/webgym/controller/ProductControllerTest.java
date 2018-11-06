package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @Mock
    ProductService productService;

    @Mock
    Model model;

    ProductController controller;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        controller = new ProductController(productService);
    }

    @Test
    public void showById() {

        Product product = new Product();
        product.setId(1L);

        when(productService.findProductById(1L)).thenReturn(product);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

        String viewName = controller.showById(String.valueOf(product.getId()), model);

        assertEquals("product/show", viewName);
        verify(productService, times(1)).findProductById(1L);
        verify(model, times(1))
                .addAttribute(eq("product"), argumentCaptor.capture());
        Product productInController = argumentCaptor.getValue();
        assertEquals(product, productInController);
    }
}