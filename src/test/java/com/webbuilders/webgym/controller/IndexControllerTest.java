package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    ProductService productService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        controller = new IndexController(productService);
    }

    @Test
    public void testMockMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("homepage"));
    }

    @Test
    public void getIndexPage() {

        Set<Product> products = new HashSet<>();
        products.add(new Product());

        Product product = new Product();
        product.setId(1L);

        products.add(product);

        when(productService.getProducts()).thenReturn(products);

        ArgumentCaptor<Set<Product>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        String viewName = controller.getIndexPage(model);

        assertEquals("index", viewName);
        verify(productService, times(1)).getProducts();
        verify(model, times(1)).addAttribute(eq("products"), argumentCaptor.capture());
        Set<Product> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}