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

    private final ComponentToComponentCommand componentConverter;

    public DetailToDetailCommand(ComponentToComponentCommand componentConverter) {
        this.componentConverter = componentConverter;
    }

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

        if (detail.getAllergen_info() != null && detail.getAllergen_info().size() > 0){
            detail.getAllergen_info()
                    .forEach((Components component) -> command.getAllergen_info().add(componentConverter.convert(component)));
        }

        if (detail.getIngredients() != null && detail.getIngredients().size() > 0){
            detail.getIngredients()
                    .forEach((Components component) -> command.getIngredients().add(componentConverter.convert(component)));
        }

        return command;
    }
}
