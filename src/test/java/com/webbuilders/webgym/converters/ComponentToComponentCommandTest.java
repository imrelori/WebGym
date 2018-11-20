package com.webbuilders.webgym.converters;

import com.webbuilders.webgym.commands.ComponentsCommand;
import com.webbuilders.webgym.domain.Components;
import com.webbuilders.webgym.domain.Details;
import org.junit.Before;
import org.junit.Test;

import javax.xml.soap.Detail;

import static org.junit.Assert.*;

public class ComponentToComponentCommandTest {

    ComponentToComponentCommand componentToComponentCommand;
    Components components;
    Long id = 1L;
    String name = "name";

    @Before
    public void setUp() throws Exception {

        componentToComponentCommand = new ComponentToComponentCommand();
        components = new Components();

        components.setId(id);
        components.setName(name);
    }

    @Test
    public void convertComponentIsNull() {

        components = null;
        assertEquals(componentToComponentCommand.convert(components), null);
    }

    @Test
    public void convertDetailsIsNull() {

        ComponentsCommand result = componentToComponentCommand.convert(components);

        assertEquals(result.getId(), id);
        assertEquals(result.getName(), name);
    }

    @Test
    public void convertDetailsIsNotNull() {

        Long detailsId = 2L;
        Details details = new Details();
        details.setId(detailsId);
        components.setDetails(details);

        ComponentsCommand result = componentToComponentCommand.convert(components);

        assertEquals(result.getId(), detailsId);
        assertEquals(result.getName(), name);
    }
}