Security
===========================================

http://jackrabbit.510166.n4.nabble.com/jira-Created-JCR-1920-Upgrade-from-1-4-5-to-1-5-creates-exception-for-LDAP-authentication-tc542196.html

The security configuration element is used to specify authentication and authorization settings for the repository. 
The structure of the security configuration element is:

```xml
<Security appName="Jackrabbit">
  <SecurityManager .../> <!-- optional, available since 1.5 -->
  <AccessManager .../>   <!-- mandatory until 1.4, optional since 1.5 -->
  <LoginModule .../>     <!-- optional -->
</Security>
```

By default Jackrabbit uses the Java Authentication and Authorization Service (JAAS) to authenticate users who try to 
access the repository. The appName parameter in the ```<Security/>``` element is used as the JAAS application name of 
the repository.

If JAAS authentication is not available or (as is often the case) too complex to set up, Jackrabbit allows you to 
specify a repository-specific JAAS LoginModule that is then used for authenticating repository users. The default 
```SimpleLoginModule``` class included in Jackrabbit implements a trivially simple authentication mechanism that accepts 
any username and any password as valid authentication credentials.

Once a user has been authenticated, Jackrabbit will use the configured ```AccessManager``` to control what parts of the 
repository content the user is allowed to access and modify. The default ```SimpleAccessManager``` class included in 
Jackrabbit implements a trivially simple authorization mechanism that grants full read access to all users and write 
access to everyone except anonymous users.

The slightly more advanced ```SimpleJBossAccessManager``` class was added in Jackrabbit 1.3 (see JCR-650). This class is 
designed for use with the JBoss Application Server, where it maps JBoss roles to Jackrabbit permissions.
