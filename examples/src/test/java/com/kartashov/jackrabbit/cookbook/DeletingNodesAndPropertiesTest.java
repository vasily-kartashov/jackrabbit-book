package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class DeletingNodesAndPropertiesTest extends AbstractTest {

    @Test
    public void deleteNodeAndPropertiesTest() throws RepositoryException {

        Node n0 = session.getRootNode().addNode("message");
        n0.setProperty("content", "Hey, there!");
        session.save();

        Node n1 = session.getNode("/message");
        assert n0 != n1;

        n1.setProperty("content", (String) null);
        session.save();

        Node n2 = session.getNode("/message");
        assert n1 != n2;
        assert !n2.hasProperty("content");

        session.removeItem("/message");
        assert !session.itemExists("/message");
        session.save();
        assert !session.itemExists("/message");
    }
}
