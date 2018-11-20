package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.domain.Components;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComponentCommandToComponentTest {

    ComponentCommandToComponent componentCommandToComponent;
    ComponentsCommand componentsCommand;
    Long id = 1L;
    String name = "name";

    @Before
    public void setUp() throws Exception {

        componentCommandToComponent = new ComponentCommandToComponent();
        componentsCommand = new ComponentsCommand();
    }

    @Test
    public void convertNull() {

        componentsCommand = null;
        assertEquals(componentCommandToComponent.convert(componentsCommand), null);
    }

    @Test
    public void convert() {

        componentsCommand.setId(id);
        componentsCommand.setName(name);
        Components result = componentCommandToComponent.convert(componentsCommand);

        assertEquals(result.getId(), id);
        assertEquals(result.getName(), name);
    }
}