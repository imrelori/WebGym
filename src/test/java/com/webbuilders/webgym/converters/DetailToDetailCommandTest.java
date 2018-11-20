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

    ComponentToComponentCommand componentToComponentCommand;
    DetailToDetailCommand detailToDetailCommand;
    Details details;
    Long id = 1L;
    Long productId = 10L;
    String dosage = "One";

    @Before
    public void setUp() throws Exception {

        componentToComponentCommand = new ComponentToComponentCommand();
        detailToDetailCommand = new DetailToDetailCommand(componentToComponentCommand);
        details = new Details();
        details.setId(id);
        details.setDosage(dosage);
    }

    @Test
    public void convertIsNull() {

        details = null;

        assertEquals(detailToDetailCommand.convert(details), null);
    }

    @Test
    public void convertAllFalse() {

        DetailsCommand result = detailToDetailCommand.convert(details);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);
        assertTrue(result.getAllergen_info().isEmpty());
        assertTrue(result.getIngredients().isEmpty());
    }

    @Test
    public void convert() {

        Product product = new Product();
        product.setId(productId);
        details.setProduct(product);

        String allergenName = "allergenName";
        Components allergen = new Components();
        allergen.setName(allergenName);
        Set<Components> allergens = new HashSet<>();
        allergens.add(allergen);
        details.setAllergen_info(allergens);

        String ingredientName = "ingredientName";
        Components ingredient = new Components();
        ingredient.setName(ingredientName);
        Set<Components> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        details.setIngredients(ingredients);

        DetailsCommand result = detailToDetailCommand.convert(details);

        assertEquals(result.getId(), productId);
        assertEquals(result.getDosage(), dosage);
        assertEquals(result.getAllergen_info().size(), 1);
        assertEquals(result.getAllergen_info().stream().findFirst().get().getName(), allergenName);
        assertEquals(result.getIngredients().size(), 1);
        assertEquals(result.getIngredients().stream().findFirst().get().getName(), ingredientName);
    }
}