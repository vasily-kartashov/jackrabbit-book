package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.commons.JcrUtils;
import org.junit.Test;

import javax.jcr.Item;
import javax.jcr.Node;
import java.io.File;
import java.io.FileInputStream;

public class UsingJcrUtilsTest extends AbstractTest {

    @Test
    public void useJcrUtils() throws Exception {

        // don't duplicate the tree

        Node australia = JcrUtils.getOrAddNode(session.getRootNode(), "australia");
        Node sydney = JcrUtils.getOrAddNode(australia, "sydney");
        Node sydneyCopy = JcrUtils.getOrAddNode(australia, "sydney");

        assert sydney.isSame(sydneyCopy);

        // better way to handle binary data

        File file = new File(getClass().getResource("/image.png").toURI());
        JcrUtils.putFile(sydney, "image", "image/png", new FileInputStream(file));

        // default values when reading nodes

        assert JcrUtils.getLongProperty(sydney, "population", 0L) == 0L;
        assert JcrUtils.getNodeIfExists(australia, "melbourne") == null;

        // java 5 style iterators

        for (Item child : JcrUtils.getChildNodes(australia)) {
            assert child.isNode();
        }
    }
}
