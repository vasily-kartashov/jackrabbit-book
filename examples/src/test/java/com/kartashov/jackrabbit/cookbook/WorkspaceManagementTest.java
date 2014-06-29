package com.kartashov.jackrabbit.cookbook;

import org.junit.Ignore;
import org.junit.Test;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Ignore
public class WorkspaceManagementTest extends AbstractTest {

    @Test
    public void manageWorkspaces() throws RepositoryException {
        session.getWorkspace().createWorkspace("temporary-workspace");

        Credentials credentials = new SimpleCredentials("admin", "admin".toCharArray());
        Session s0 = repository.login(credentials, "temporary-workspace");
        s0.getRootNode().addNode("node");
        s0.save();
        s0.logout();
    }
}
