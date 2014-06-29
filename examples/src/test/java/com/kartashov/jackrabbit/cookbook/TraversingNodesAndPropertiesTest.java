package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

public class TraversingNodesAndPropertiesTest extends AbstractTest {

    @Test
    public void traverseNodesAndProperties() throws Exception {

        Node australia = session.getRootNode().addNode("australia");
        Node canberra = australia.addNode("canberra");
        canberra.setProperty("capital", true);
        Node sydney = australia.addNode("sydney");
        sydney.setProperty("population", 4757083L);

        Node melbourne = australia.addNode("melbourne");
        Node perth = australia.addNode("perth");
        Node darwin = australia.addNode("darwin");
        Node brisbane = australia.addNode("brisbane");
        Node adelaide = australia.addNode("adelaide");
        session.save();

        assert australia.hasNodes();
        assert canberra.hasProperties();
        assert !canberra.hasNodes();

        NodeIterator nodeIterator = australia.getNodes();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.nextNode();
            PropertyIterator propertyIterator = node.getProperties();
            while (propertyIterator.hasNext()) {
                Property property = propertyIterator.nextProperty();
                System.out.println("Node " + node.getName() + " has property " + property.getName());
            }
        }

        nodeIterator = australia.getNodes("sydney-*|melbourne-*");
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.nextNode();
            PropertyIterator propertyIterator = node.getProperties(new String[]{
                    "capital",
                    "Capital",
                    "population",
                    "Population"
            });
            while (propertyIterator.hasNext()) {
                Property property = propertyIterator.nextProperty();
                System.out.println("Node " + node.getName() + " has property " + property.getName());
            }
        }
    }
}
