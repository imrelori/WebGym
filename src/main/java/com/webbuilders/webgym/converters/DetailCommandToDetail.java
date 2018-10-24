package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.domain.Details;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DetailCommandToDetail implements Converter<DetailsCommand, Details> {

    private final ComponentCommandToComponent componentConverter;

    public DetailCommandToDetail(ComponentCommandToComponent componentConverter) {
        this.componentConverter = componentConverter;
    }

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

        if (source.getAllergen_info() != null && source.getAllergen_info().size() > 0){
            source.getAllergen_info()
                    .forEach(component -> details.getAllergen_info().add(componentConverter.convert(component)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(component -> details.getIngredients().add(componentConverter.convert(component)));
        }

        return details;
    }

}
