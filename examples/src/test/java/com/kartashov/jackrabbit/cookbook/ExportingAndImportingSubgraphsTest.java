package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.RepositoryException;
import java.io.*;

public class ExportingAndImportingSubgraphsTest extends AbstractTest {

    @Test
    public void exportAndImportSubgraphs() throws RepositoryException, IOException {

        session.getRootNode().addNode("message");
        session.save();

        File file = File.createTempFile("jcr", ".xml");

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        session.exportSystemView("/message", out, false, false);
        out.close();

        session.removeItem("/message");
        session.save();
        assert !session.nodeExists("/message");

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        session.getWorkspace().importXML("/", in, ImportUUIDBehavior.IMPORT_UUID_COLLISION_REMOVE_EXISTING);
        in.close();
        file.delete();

        assert session.nodeExists("/message");
    }
}
