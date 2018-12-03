package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DetailCommandToDetail implements Converter<DetailsCommand, Details> {

    @Synchronized
    @Nullable
    @Override
    public Details convert(DetailsCommand source) {
        if (source == null) {
            return null;
        }

        final Details details = new Details();

        details.setId(source.getId());
        if (source.getProduct() != null) {
            Product product = new Product();
            product.setId(source.getProductId());
            details.setProduct(product);
            product.addDetails(details);
        }
        details.setDosage(source.getDosage());
        details.setIngredients(source.getIngredient());
        details.setFlavor_name(source.getFlavor_name());
        details.setAllergen_info(source.getAllergen_info());

        return details;
    }

}
