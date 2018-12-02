package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Components;
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
            command.setId(detail.getProduct().getId());
        }
        command.setDosage(detail.getDosage());
        command.setIngredient(detail.getIngredients());

        return command;
    }
}
