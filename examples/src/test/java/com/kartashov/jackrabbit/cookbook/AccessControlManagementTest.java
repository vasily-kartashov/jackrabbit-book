package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;
import org.junit.Test;

import javax.jcr.Credentials;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.security.*;
import java.security.Principal;

public class AccessControlManagementTest extends AbstractTest {

    @Test
    public void manageAccessControl() throws RepositoryException {

        AccessControlManager accessControlManager = session.getAccessControlManager();

        UserManager userManager = ((JackrabbitSession) session).getUserManager();
        User user = userManager.createUser("contributor", "contributor");
        Principal principal = new PrincipalImpl(user.getID());
        Privilege[] privileges = new Privilege[] {
                accessControlManager.privilegeFromName(Privilege.JCR_ALL)
        };

        AccessControlPolicyIterator iterator = accessControlManager.getApplicablePolicies("/");
        while (iterator.hasNext()) {
            AccessControlPolicy policy = iterator.nextAccessControlPolicy();
            ((AccessControlList) policy).addAccessControlEntry(principal, privileges);
            accessControlManager.setPolicy("/", policy);
        }

        Credentials c1 = new SimpleCredentials("contributor", "contributor".toCharArray());
        Session s1 = repository.login(c1);
        s1.getRootNode().addNode("node");
        s1.logout();
    }
}
