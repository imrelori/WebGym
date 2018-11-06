package com.webbuilders.webgym.services;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.converters.ComponentToComponentCommand;
import com.webbuilders.webgym.converters.DetailToDetailCommand;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailServiceTest {

    DetailServiceImpl detailService;
    DetailToDetailCommand detailToDetailCommand;
    Long productId = 1L;
    Long detailsId = 1L;

    Product product;
    Details details;

    @Mock
    ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        ComponentToComponentCommand cc = new ComponentToComponentCommand();
        detailToDetailCommand = new DetailToDetailCommand(cc);

        detailService = new DetailServiceImpl(productRepository, detailToDetailCommand);
        product = new Product();
        details = new Details();
    }

    @Test
    public void findByProductIdAndDetailId() throws Exception {

        product.setId(productId);
        details.setId(detailsId);
        product.addDetails(details);

        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        Optional<DetailsCommand> detailCommandOptional = product.getDetails().stream()
                .filter(detail -> detail.getId().equals(detailsId))
                .map(detailToDetailCommand::convert).findFirst();

        when(productRepository.findById(productId)).thenReturn(productOptional);

        DetailsCommand result = detailService.findByProductIdAndDetailId(productId, detailsId);
        assertEquals(result, detailCommandOptional.get());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test(expected = RuntimeException.class)
    public void findByProductIdAndDetailIdNoProduct() throws Exception {

        product.setId(2L);

        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productRepository.findById(productId)).thenReturn(productOptional);
        detailService.findByProductIdAndDetailId(productId, 1L);

        verify(productRepository, times(1)).findById(productId);
    }

    @Test(expected = RuntimeException.class)
    public void findByProductIdAndDetailIdNoDetails() throws Exception {

        product.setId(1L);
        details.setId(2L);
        product.addDetails(details);

        Optional<Product> productOptional = Optional.of(product)
                .filter(e -> e.getId().equals(productId));

        when(productRepository.findById(productId)).thenReturn(productOptional);
        detailService.findByProductIdAndDetailId(productId, 1L);

        verify(productRepository, times(1)).findById(productId);
    }

}
