package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.*;

public class UsingVisitorPatternTest extends AbstractTest {

    @Test
    public void useVisitorPattern() throws Exception {

        Node australia = session.getRootNode().addNode("australia");
        Node canberra = australia.addNode("canberra");
        canberra.setProperty("capital", true);
        Node sydney = australia.addNode("sydney");
        sydney.setProperty("population", 4757083L);
        australia.addNode("melbourne");
        australia.addNode("perth");
        australia.addNode("darwin");
        australia.addNode("brisbane");
        australia.addNode("adelaide");

        ItemVisitor jsonVisitor = new ItemVisitor() {

            protected StringBuffer output = new StringBuffer();

            @Override
            public void visit(Property property) throws RepositoryException {
                output.append('\"').append(property.getName()).append("\":");
                Value value = property.getValue();
                switch (value.getType()) {
                    case PropertyType.LONG:
                        output.append(value.getLong());
                        break;
                    case PropertyType.BOOLEAN:
                        output.append(value.getBoolean());
                        break;
                    case PropertyType.STRING:
                    case PropertyType.NAME:
                        output.append('\"').append(value.getString()).append('\"');
                        break;
                    default:
                        output.append("null");
                }
            }

            @Override
            public void visit(Node node) throws RepositoryException {
                output.append('{');
                NodeIterator nodeIterator = node.getNodes();
                while (nodeIterator.hasNext()) {
                    Node child = nodeIterator.nextNode();
                    output.append('\"').append(child.getName()).append('\"').append(':');
                    child.accept(this);
                    if (nodeIterator.hasNext()) {
                        output.append(',');
                    }
                }
                PropertyIterator propertyIterator = node.getProperties();
                if (node.hasNodes() && node.hasProperties()) {
                    output.append(',');
                }
                while (propertyIterator.hasNext()) {
                    Property property = propertyIterator.nextProperty();
                    property.accept(this);
                    if (propertyIterator.hasNext()) {
                        output.append(',');
                    }
                }
                output.append('}');
            }

            public String toString() {
                return output.toString();
            }
        };

        australia.accept(jsonVisitor);
        System.out.println(jsonVisitor.toString());
    }
}
