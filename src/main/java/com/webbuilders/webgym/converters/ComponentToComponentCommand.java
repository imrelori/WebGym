package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.domain.Components;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComponentToComponentCommand implements Converter<Components, ComponentsCommand> {

    public ComponentToComponentCommand() {
    }

    @Synchronized
    @Nullable
    @Override
    public ComponentsCommand convert(Components component) {
        if (component == null) {
            return null;
        }

        ComponentsCommand componentsCommand= new ComponentsCommand();

        componentsCommand.setId(component.getId());
        if (component.getDetails() != null) {
            componentsCommand.setId(component.getDetails().getId());
        }

        componentsCommand.setName(component.getName());

        return componentsCommand;
    }
}