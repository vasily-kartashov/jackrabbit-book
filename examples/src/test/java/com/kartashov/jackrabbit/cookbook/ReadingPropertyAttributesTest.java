package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.JcrConstants;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.Value;
import javax.jcr.ValueFactory;

public class ReadingPropertyAttributesTest extends AbstractTest {

    @Test
    public void readPropertyAttributes() throws Exception {

        ValueFactory valueFactory = session.getValueFactory();

        Node australia = session.getRootNode().addNode("australia");
        Node canberra = australia.addNode("canberra");
        canberra.addMixin(JcrConstants.MIX_REFERENCEABLE);

        australia.setProperty("name", "Australia");
        australia.setProperty("continent", true);
        australia.setProperty("population", 23457161L);
        australia.setProperty("states", new Value[]{
                valueFactory.createValue("Australian Capital Territory"),
                valueFactory.createValue("New South Wales"),
                valueFactory.createValue("Queensland"),
                valueFactory.createValue("South Australia"),
                valueFactory.createValue("Tasmania"),
                valueFactory.createValue("Victoria"),
                valueFactory.createValue("Western Australia")
        });
        australia.setProperty("capital", canberra);

        assert australia.getProperty("continent").getType() == PropertyType.BOOLEAN;
        assert australia.getProperty("states").isMultiple();
        assert australia.getProperty("name").getLength() == "Australia".length();
        assert australia.getProperty("states").getLengths()[0] == "Australian Capital Territory".length();
        assert australia.getProperty("capital").getNode().isSame(canberra);
    }
}
