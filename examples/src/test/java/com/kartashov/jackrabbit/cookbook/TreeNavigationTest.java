package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.Node;

public class TreeNavigationTest extends AbstractTest {

    @Test
    public void navigateTree() throws Exception {
        
        Node australia = session.getRootNode().addNode("australia");
        Node sydney = australia.addNode("sydney");
        Node manly = sydney.addNode("manly");
        Node beach = manly.addNode("beach");
        session.save();

        assert beach.getName().equals("beach");
        assert beach.getPath().equals("/australia/sydney/manly/beach");

        assert beach.getAncestor(0).isSame(session.getRootNode());
        assert beach.getAncestor(1).isSame(australia);
        assert beach.getAncestor(2).isSame(sydney);

        assert beach.getParent().isSame(manly);

        assert beach.getDepth() == 4;
    }
}
