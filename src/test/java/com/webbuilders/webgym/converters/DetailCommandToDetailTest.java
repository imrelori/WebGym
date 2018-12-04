package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DetailCommandToDetailTest {

    DetailCommandToDetail detailCommandToDetail;
    DetailsCommand detailsCommand;
    Long id = 1L;
    Long productId = 10L;
    String dosage = "One";

    @Before
    public void setUp() throws Exception {

        detailCommandToDetail = new DetailCommandToDetail();
        detailsCommand = new DetailsCommand();
        detailsCommand.setId(id);
        detailsCommand.setDosage(dosage);
    }

    @Test
    public void convertDetailsCommandIsNull() {

        detailsCommand = null;

        assertNull(detailCommandToDetail.convert(detailsCommand));
    }

    @Test
    public void convertAllFalse() {

        Details result = detailCommandToDetail.convert(detailsCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);
        assertNull(result.getAllergen_info());
        assertNull(result.getIngredients());
        assertNull(result.getFlavor_name());
    }

    @Test
    public void convert() {

        Product product = new Product();
        product.setId(productId);
        detailsCommand.setProduct(product);

        String allergen = "allergen";
        detailsCommand.setAllergen_info(allergen);

        String ingredient = "ingredient";
        detailsCommand.setIngredient(ingredient);

        String flavor_name = "Choco";
        detailsCommand.setFlavor_name(flavor_name);

        Details result = detailCommandToDetail.convert(detailsCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);assertEquals(result.getFlavor_name(), flavor_name);
        assertEquals(result.getIngredients(), ingredient);
        assertEquals(result.getAllergen_info(), allergen);
    }
}