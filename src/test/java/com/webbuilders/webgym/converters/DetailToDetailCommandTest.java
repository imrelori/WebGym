package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Components;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DetailToDetailCommandTest {

    DetailToDetailCommand detailToDetailCommand;
    Details details;
    Long id = 1L;
    Long productId = 10L;
    String dosage = "One";

    @Before
    public void setUp() throws Exception {

        detailToDetailCommand = new DetailToDetailCommand();
        details = new Details();
        details.setId(id);
        details.setDosage(dosage);
    }

    @Test
    public void convertIsNull() {

        details = null;

        assertNull(detailToDetailCommand.convert(details));
    }

    @Test
    public void convertAllFalse() {

        DetailsCommand result = detailToDetailCommand.convert(details);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);
        assertNull(result.getAllergen_info());
        assertNull(result.getIngredient());
        assertNull(result.getFlavor_name());
    }

    @Test
    public void convert() {

        Product product = new Product();
        product.setId(productId);
        details.setProduct(product);

        String allergen = "allergen";
        details.setAllergen_info(allergen);

        String ingredient = "ingredient";
        details.setIngredients(ingredient);

        String flavor_name = "Choco";
        details.setFlavor_name(flavor_name);

        DetailsCommand result = detailToDetailCommand.convert(details);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);
        assertEquals(result.getFlavor_name(), flavor_name);
        assertEquals(result.getIngredient(), ingredient);
        assertEquals(result.getAllergen_info(), allergen);
    }
}