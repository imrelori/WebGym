package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.services.DetailService;
import com.webbuilders.webgym.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailControllerTest {

    @Mock
    DetailService detailService;

    @Mock
    ProductService productService;

    @Mock
    Model model;

    DetailController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new DetailController(productService, detailService);
    }

    @Test
    public void listDetails() {

        Product product = new Product();
        product.setId(1L);

        when(productService.findProductById(1L)).thenReturn(product);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

        String viewName = controller.listDetails(String.valueOf(product.getId()), model);

        assertEquals("product/details/list", viewName);
        verify(productService, times(1)).findProductById(1L);
        verify(model, times(1))
                .addAttribute(eq("product"), argumentCaptor.capture());
        Product productInController = argumentCaptor.getValue();
        assertEquals(product, productInController);
    }

    @Test
    public void showProductDetails() {

        Product product = new Product();
        product.setId(1L);

        Details details = new Details();
        details.setId(1L);

        DetailsCommand detailsCommand = new DetailsCommand();

        when(detailService.findByProductIdAndDetailId(1L, 1L))
                .thenReturn(detailsCommand);

        ArgumentCaptor<DetailsCommand> argumentCaptor =
                ArgumentCaptor.forClass(DetailsCommand.class);

        String viewName = controller.showProductDetails(String.valueOf(product.getId())
                                                      , String.valueOf(details.getId())
                                                      , model);

        assertEquals("product/details/showDetail", viewName);
        verify(detailService, times(1)).findByProductIdAndDetailId(1L, 1L);
        verify(model, times(1)).addAttribute(eq("details"), argumentCaptor.capture());
        DetailsCommand detailsCommandInController = argumentCaptor.getValue();
        assertEquals(detailsCommand, detailsCommandInController);
    }
}