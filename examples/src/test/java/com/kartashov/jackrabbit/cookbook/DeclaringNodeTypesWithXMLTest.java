package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.api.JackrabbitNodeTypeManager;
import org.junit.Test;

import javax.jcr.Node;
import java.io.File;
import java.io.FileInputStream;

public class DeclaringNodeTypesWithXMLTest extends AbstractTest {

    @Test
    public void declareNodeTypesWithXML() throws Exception {

        JackrabbitNodeTypeManager nodeTypeManager = (JackrabbitNodeTypeManager) session.getWorkspace().getNodeTypeManager();
        File file = file = new File(getClass().getResource("/node-types.xml").toURI());
        nodeTypeManager.registerNodeTypes(new FileInputStream(file), JackrabbitNodeTypeManager.TEXT_XML);

        Node n0 = session.getRootNode().addNode("australia", "app:country");

        assert n0.hasProperty("app:name");

        Node n1 = n0.addNode("canberra", "app:city");

        assert n1.hasProperty("app:name");
        assert n1.hasProperty("app:population");

        n0.setProperty("app:capital", n1);
    }
}
