Pure in-memory repository
===================================================

```xml
<?xml version="1.0"?>
<!DOCTYPE Repository
        PUBLIC "-//The Apache Software Foundation//DTD Jackrabbit 2.0//EN"
        "http://jackrabbit.apache.org/dtd/repository-2.0.dtd">
<Repository>
    <FileSystem class="org.apache.jackrabbit.core.fs.mem.MemoryFileSystem">
    </FileSystem>
    <DataStore class="org.apache.jackrabbit.core.data.FileDataStore"/>
    <Security appName="Jackrabbit">
        <SecurityManager class="org.apache.jackrabbit.core.DefaultSecurityManager" workspaceName="security">
        </SecurityManager>
        <AccessManager class="org.apache.jackrabbit.core.security.DefaultAccessManager">
        </AccessManager>
        <LoginModule class="org.apache.jackrabbit.core.security.authentication.DefaultLoginModule">
            <param name="anonymousId" value="anonymous"/>
            <param name="adminId" value="admin"/>
        </LoginModule>
    </Security>
    <Workspaces rootPath="${rep.home}/workspaces" defaultWorkspace="default"/>
    <Workspace name="${wsp.name}">
        <FileSystem class="org.apache.jackrabbit.core.fs.mem.MemoryFileSystem"/>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.BundleFsPersistenceManager">
            <param name="blobFSBlockSize" value="1"/>
        </PersistenceManager>
        <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
            <param name="path" value="${wsp.home}/index"/>
            <param name="supportHighlighting" value="true"/>
            <param name="directoryManagerClass" value="org.apache.jackrabbit.core.query.lucene.directory.RAMDirectoryManager"/>
            <FileSystem class="org.apache.jackrabbit.core.fs.mem.MemoryFileSystem"></FileSystem>
        </SearchIndex>
    </Workspace>
    <Versioning rootPath="${rep.home}/version">
        <FileSystem class="org.apache.jackrabbit.core.fs.mem.MemoryFileSystem">
        </FileSystem>
        <PersistenceManager class="org.apache.jackrabbit.core.persistence.bundle.BundleFsPersistenceManager">
            <param name="blobFSBlockSize" value="1"/>
        </PersistenceManager>
    </Versioning>
    <SearchIndex class="org.apache.jackrabbit.core.query.lucene.SearchIndex">
        <param name="path" value="${rep.home}/repository/index"/>
        <param name="supportHighlighting" value="true"/>
        <param name="directoryManagerClass" value="org.apache.jackrabbit.core.query.lucene.directory.RAMDirectoryManager"/>
        <FileSystem class="org.apache.jackrabbit.core.fs.mem.MemoryFileSystem"></FileSystem>
    </SearchIndex>
    <Cluster id="node1">
        <Journal class="org.apache.jackrabbit.core.journal.FileJournal">
            <param name="revision" value="${rep.home}/revision.log"/>
            <param name="directory" value="${rep.home}/myjournal"/>
        </Journal>
    </Cluster>
</Repository>
```

You then can create a ```TransientRepository``` by executing (todo is it the best way?)
```java
File configuration = new File(getClass().getResource("/repository.xml").toURI());
File directory = new File(System.getProperty("java.io.tmpdir"));
repository = new TransientRepository(configuration, directory);
```
