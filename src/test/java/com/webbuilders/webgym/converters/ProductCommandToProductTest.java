package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.CategoryCommand;
import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.domain.CartLine;
import com.webbuilders.webgym.domain.Level;
import com.webbuilders.webgym.domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProductCommandToProductTest {

    CategoryCommandToCategory categoryCommandToCategory;
    DetailCommandToDetail detailCommandToDetail;

    ProductCommandToProduct productCommandToProduct;
    ProductCommand productCommand;

    Long id = 1L;
    String name = "product";
    String url = "https://product.hu";
    Integer servings = 2;
    String pack = "Big";
    String description = "description";
    Integer price = 100;
    CartLine cartLine = new CartLine();
    Level level = Level.ADVANCED;

    @Before
    public void setUp() throws Exception {

        categoryCommandToCategory = new CategoryCommandToCategory();
        detailCommandToDetail = new DetailCommandToDetail();
        productCommandToProduct = new ProductCommandToProduct(categoryCommandToCategory, detailCommandToDetail);

        productCommand = new ProductCommand();
        productCommand.setId(id);
        productCommand.setCartLine(cartLine);
        productCommand.setDescription(description);
        productCommand.setLevel(level);
        productCommand.setName(name);
        productCommand.setPack(pack);
        productCommand.setPrice(price);
        productCommand.setServings(servings);
        productCommand.setUrl(url);
    }

    @Test
    public void convertProductCommandIsNull() {

        productCommand = null;

        assertNull(productCommandToProduct.convert(productCommand));
    }

    @Test
    public void convertAllFalse() {

        Product result = productCommandToProduct.convert(productCommand);

        assertEquals(result.getId(), id);
        assertTrue(result.getDetails().isEmpty());
        assertTrue(result.getCategories().isEmpty());
        assertEquals(result.getCartLine(), cartLine);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getLevel(), level);
        assertEquals(result.getName(), name);
        assertEquals(result.getPack(), pack);
        assertEquals(result.getPrice(), price);
        assertEquals(result.getServings(), servings);
        assertEquals(result.getUrl(), url);
    }

    @Test
    public void convert() {

        Long categoryId = 100L;
        CategoryCommand category = new CategoryCommand();
        category.setId(categoryId);
        Set<CategoryCommand> categories = new HashSet<>();
        categories.add(category);
        productCommand.setCategories(categories);

        Long detailId = 200L;
        DetailsCommand detail = new DetailsCommand();
        detail.setId(detailId);
        Set<DetailsCommand> details = new HashSet<>();
        details.add(detail);
        productCommand.setDetails(details);

        Product result = productCommandToProduct.convert(productCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getDetails().size(), 1);
        assertEquals(result.getDetails().stream().findFirst().get().getId(), detailId);
        assertEquals(result.getCategories().size(), 1);
        assertEquals(result.getCategories().stream().findFirst().get().getId(), categoryId);
        assertEquals(result.getCartLine(), cartLine);
        assertEquals(result.getDescription(), description);
        assertEquals(result.getLevel(), level);
        assertEquals(result.getName(), name);
        assertEquals(result.getPack(), pack);
        assertEquals(result.getPrice(), price);
        assertEquals(result.getServings(), servings);
        assertEquals(result.getUrl(), url);
    }
}