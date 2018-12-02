package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
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
            details.setId(source.getProduct().getId());
        }
        details.setDosage(source.getDosage());
        details.setIngredients(source.getIngredient());

        return details;
    }

}
