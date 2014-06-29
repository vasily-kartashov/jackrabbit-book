package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.*;

public class CreatingNodesAndPropertiesTest extends AbstractTest {

    @Test
    public void createNodesAndProperties() throws RepositoryException {
        Node n0 = session.getRootNode().addNode("message");
        n0.setProperty("content", "Hey, there!");
        session.save();

        Node n1 = session.getNode("/message");
        assert n1.getProperty("content").getString().equals("Hey, there!");
    }
}
