Access control management
=====================================

@todo essential to write down what these privileges are and how the policies are organized

```java
AccessControlManager accessControlManager = session.getAccessControlManager();

UserManager userManager = ((JackrabbitSession) session).getUserManager();
User user = userManager.createUser("contributor", "contributor");
Principal principal = new PrincipalImpl(user.getID());
Privilege[] privileges = new Privilege[] {
        accessControlManager.privilegeFromName(Privilege.JCR_ALL)
};

AccessControlPolicyIterator iterator = accessControlManager
    .getApplicablePolicies("/");
while (iterator.hasNext()) {
    AccessControlPolicy policy = iterator.nextAccessControlPolicy();
    ((AccessControlList) policy).addAccessControlEntry(principal, privileges);
    accessControlManager.setPolicy("/", policy);
}

Credentials c1 = new SimpleCredentials("contributor", 
    "contributor".toCharArray());
Session s1 = repository.login(c1);
s1.getRootNode().addNode("node");
s1.logout();
```