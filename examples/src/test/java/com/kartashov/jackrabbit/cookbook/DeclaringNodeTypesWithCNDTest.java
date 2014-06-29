package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.commons.cnd.CndImporter;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.nodetype.NodeType;
import java.io.File;
import java.io.FileReader;

public class DeclaringNodeTypesWithCNDTest extends AbstractTest {

    @Test
    public void declareNodeTypesWithCND() throws Exception {

        File file = file = new File(getClass().getResource("/node-types.cnd").toURI());
        NodeType[] nodeTypes = CndImporter.registerNodeTypes(new FileReader(file), session);

        Node n0 = session.getRootNode().addNode("australia", "app:country");

        assert n0.hasProperty("app:name");

        Node n1 = n0.addNode("canberra", "app:city");

        assert n1.hasProperty("app:name");
        assert n1.hasProperty("app:population");

        n0.setProperty("app:capital", n1);
    }
}
