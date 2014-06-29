package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.JcrConstants;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.ValueFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

public class UsingBinaryDataTest extends AbstractTest {

    @Test
    public void useBinaryData() throws Exception {

        ValueFactory valueFactory = session.getValueFactory();

        Node n0 = session.getRootNode().addNode("image", JcrConstants.NT_FILE);
        Node n1 = n0.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);

        File file = new File(getClass().getResource("/image.png").toURI());
        n1.setProperty(JcrConstants.JCR_DATA, valueFactory.createBinary(new FileInputStream(file)));
        n1.setProperty(JcrConstants.JCR_MIMETYPE, "image/png");
        n1.setProperty(JcrConstants.JCR_LASTMODIFIED, Calendar.getInstance().getTimeInMillis());

        assert file.length() == session.getProperty("/image/jcr:content/jcr:data").getBinary().getSize();
    }
}
