package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandToProduct implements Converter<ProductCommand, Product> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final DetailCommandToDetail detailCommandToDetail;

    public ProductCommandToProduct(CategoryCommandToCategory categoryCommandToCategory, DetailCommandToDetail detailCommandToDetail) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.detailCommandToDetail = detailCommandToDetail;
    }

    @Synchronized
    @Nullable
    @Override
    public Product convert(ProductCommand productCommand){
        if (productCommand == null)
            return null;

        final Product product = new Product();
        product.setServings(productCommand.getServings());
        product.setUrl(productCommand.getUrl());
        product.setDescription(productCommand.getDescription());
        product.setLevel(productCommand.getLevel());
        product.setName(productCommand.getName());
        product.setPack(productCommand.getPack());
        product.setPrice(productCommand.getPrice());
        product.setCartLine(productCommand.getCartLine());
        product.setId(productCommand.getId());

        if (productCommand.getCategories() != null && productCommand.getCategories().size() > 0){
            productCommand.getCategories()
                    .forEach(category -> product.getCategories().add(categoryCommandToCategory.convert(category)));
        }

        if (productCommand.getDetails() != null && productCommand.getDetails().size() > 0){
            productCommand.getDetails()
                    .forEach(details -> product.getDetails().add(detailCommandToDetail.convert(details)));
        }

        return product;
    }

}
