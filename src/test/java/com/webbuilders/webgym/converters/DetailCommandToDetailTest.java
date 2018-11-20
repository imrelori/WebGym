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

    ComponentCommandToComponent componentCommandToComponent;
    DetailCommandToDetail detailCommandToDetail;
    DetailsCommand detailsCommand;
    Long id = 1L;
    Long productId = 10L;
    String dosage = "One";

    @Before
    public void setUp() throws Exception {

        componentCommandToComponent = new ComponentCommandToComponent();
        detailCommandToDetail = new DetailCommandToDetail(componentCommandToComponent);
        detailsCommand = new DetailsCommand();
        detailsCommand.setId(id);
        detailsCommand.setDosage(dosage);
    }

    @Test
    public void convertDetailsCommandIsNull() {

        detailsCommand = null;

        assertEquals(detailCommandToDetail.convert(detailsCommand), null);
    }

    @Test
    public void convertAllFalse() {

        Details result = detailCommandToDetail.convert(detailsCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getDosage(), dosage);
        assertTrue(result.getIngredients().isEmpty());
        assertTrue(result.getAllergen_info().isEmpty());
    }

    @Test
    public void convert() {

        Product product = new Product();
        product.setId(productId);
        detailsCommand.setProduct(product);

        String allergenName = "allergenName";
        ComponentsCommand allergen = new ComponentsCommand();
        allergen.setName(allergenName);
        Set<ComponentsCommand> allergens = new HashSet<>();
        allergens.add(allergen);
        detailsCommand.setAllergen_info(allergens);

        String ingredientName = "ingredientName";
        ComponentsCommand ingredient = new ComponentsCommand();
        ingredient.setName(ingredientName);
        Set<ComponentsCommand> ingredients = new HashSet<>();
        ingredients.add(ingredient);
        detailsCommand.setIngredients(ingredients);

        Details result = detailCommandToDetail.convert(detailsCommand);

        assertEquals(result.getId(), productId);
        assertEquals(result.getDosage(), dosage);
        assertEquals(result.getAllergen_info().size(), 1);
        assertEquals(result.getAllergen_info().stream().findFirst().get().getName(), allergenName);
        assertEquals(result.getIngredients().size(), 1);
        assertEquals(result.getIngredients().stream().findFirst().get().getName(), ingredientName);
    }
}