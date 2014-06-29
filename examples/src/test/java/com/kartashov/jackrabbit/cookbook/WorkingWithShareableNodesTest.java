package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.JcrConstants;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Workspace;

public class WorkingWithShareableNodesTest extends AbstractTest {

    @Test
    public void workWithShareableNodes() throws RepositoryException {

        Workspace workspace = session.getWorkspace();

        Node n0 = session.getRootNode().addNode("message");
        n0.addMixin(JcrConstants.MIX_SHAREABLE);
        n0.setProperty("content", "Hello");
        session.getRootNode().addNode("parent");
        session.save();

        workspace.clone(workspace.getName(), "/message", "/parent/message-copy", false);

        assert session.nodeExists("/parent/message-copy");
        assert session.getNode("/parent/message-copy").getProperty("content").getString().equals("Hello");

        n0.setProperty("content", "Good bye");
        session.save();

        assert session.getNode("/parent/message-copy").getProperty("content").getString().equals("Good bye");

        n0.remove();
        session.save();

        assert !session.nodeExists("/message");
        assert session.nodeExists("/parent/message-copy");
    }
}
