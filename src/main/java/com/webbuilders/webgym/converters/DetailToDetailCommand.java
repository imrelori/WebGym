package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DetailToDetailCommand implements Converter<Details, DetailsCommand> {

    @Synchronized
    @Nullable
    @Override
    public DetailsCommand convert(Details detail) {
        if (detail == null) {
            return null;
        }

        final DetailsCommand command = new DetailsCommand();

        command.setId(detail.getId());
        if (detail.getProduct() != null) {
            command.setProductId(detail.getProduct().getId());
        }
        command.setDosage(detail.getDosage());
        command.setIngredient(detail.getIngredients());
        command.setFlavor_name(detail.getFlavor_name());
        command.setAllergen_info(detail.getAllergen_info());

        return command;
    }
}
