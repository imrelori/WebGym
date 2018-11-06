package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    private final DetailToDetailCommand detailToDetailCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public ProductToProductCommand(DetailToDetailCommand detailToDetailCommand, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.detailToDetailCommand = detailToDetailCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public ProductCommand convert(Product product) {
        if (product == null)
            return null;

        final ProductCommand productCommand = new ProductCommand();
        productCommand.setCartLine(product.getCartLine());
        productCommand.setDescription(product.getDescription());
        productCommand.setId(product.getId());
        productCommand.setLevel(product.getLevel());
        productCommand.setName(product.getName());
        productCommand.setPack(product.getPack());
        productCommand.setPrice(product.getPrice());
        productCommand.setServings(product.getServings());
        productCommand.setUrl(product.getUrl());

        if (product.getCategories() != null && product.getCategories().size() > 0){
            product.getCategories()
                    .forEach(category -> productCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
        }

        if (product.getDetails() != null && product.getDetails().size() > 0){
            product.getDetails()
                    .forEach(details -> productCommand.getDetails().add(detailToDetailCommand.convert(details)));
        }

        return productCommand;
    }

}
