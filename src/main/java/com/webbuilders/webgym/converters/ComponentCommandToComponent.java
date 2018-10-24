package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.domain.Components;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComponentCommandToComponent implements Converter<ComponentsCommand, Components> {

    public ComponentCommandToComponent() {
    }

    @Synchronized
    @Nullable
    @Override
    public Components convert(ComponentsCommand source) {
        if (source == null) {
            return null;
        }

        final Components components = new Components();
        components.setId(source.getId());
        components.setName(source.getName());

        return components;

    }
}
