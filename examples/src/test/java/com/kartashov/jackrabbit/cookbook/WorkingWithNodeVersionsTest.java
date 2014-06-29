package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.JcrConstants;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionManager;

public class WorkingWithNodeVersionsTest extends AbstractTest {

    @Test
    public void workWithNodeVersions() throws RepositoryException {

        VersionManager versionManager = session.getWorkspace().getVersionManager();

        Node n0 = session.getRootNode().addNode("message",
                JcrConstants.NT_UNSTRUCTURED);
        n0.addMixin(JcrConstants.MIX_VERSIONABLE);
        n0.setProperty("content", "Hello");
        session.save();
        Version firstVersion = versionManager.checkin(n0.getPath());

        Node n1 = session.getNode(n0.getPath());
        versionManager.checkout(n0.getPath());
        n1.setProperty("content", "Good bye");
        session.save();
        versionManager.checkin(n0.getPath());

        Node n2 = session.getNode(n0.getPath());
        assert n2.getProperty("content").getString().equals("Good bye");

        versionManager.restore(firstVersion, true);
        Node n3 = session.getNode(n0.getPath());
        assert n3.getProperty("content").getString().equals("Hello");
    }
}
