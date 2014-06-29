package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;

public class UsingReferencesTest extends AbstractTest {

    @Test
    public void useReferences() throws Exception {

        Node country = session.getRootNode().addNode("australia");
        country.setProperty("name", "Australia");

        Node canberra = country.addNode("canberra");
        canberra.addMixin("mix:referenceable");
        canberra.setProperty("name", "Canberra");
        canberra.setProperty("population", 381488L);

        Node sydney = country.addNode("sydney");
        sydney.addMixin("mix:referenceable");
        sydney.setProperty("name", "Sydney");
        sydney.setProperty("population", 4757083L);

        Node melbourne = country.addNode("melbourne");
        melbourne.addMixin("mix:referenceable");
        melbourne.setProperty("name", "Melbourne");
        melbourne.setProperty("population", 4347955L);

        country.setProperty("nsw-capital-population", "/australia/sydney/population", PropertyType.PATH);
        country.setProperty("capital", canberra.getIdentifier(), PropertyType.WEAKREFERENCE);
        country.setProperty("victoria-capital", melbourne);

        String populationPropertyPath = country.getProperty("nsw-capital-population").getString();
        assert session.getProperty(populationPropertyPath).getLong() == 4757083L;

        String capitalIdentifier = country.getProperty("capital").getValue().getString();
        assert session.getNodeByIdentifier(capitalIdentifier).getProperty("name").getString().equals("Canberra");

        String victoriaCapitalIdentifier = country.getProperty("victoria-capital").getString();
        assert session.getNodeByIdentifier(victoriaCapitalIdentifier).getProperty("name").getString().equals("Melbourne");

        assert country.getProperty("nsw-capital-population").getProperty().getLong() == 4757083L;
        assert country.getProperty("capital").getNode().getProperty("name").getString().equals("Canberra");
        assert country.getProperty("victoria-capital").getNode().getProperty("name").getString().equals("Melbourne");

        PropertyIterator iterator1 = canberra.getWeakReferences();
        while (iterator1.hasNext()) {
            Property property = iterator1.nextProperty();
            assert property.getParent().getProperty("name").getString().equals("Australia");
        }

        PropertyIterator iterator2 = melbourne.getReferences();
        while (iterator2.hasNext()) {
            Property property = iterator2.nextProperty();
            assert property.getParent().getProperty("name").getString().equals("Australia");
        }

        sydney.remove();
        canberra.remove();
    }
}
