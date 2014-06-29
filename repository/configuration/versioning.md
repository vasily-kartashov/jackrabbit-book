Versioning
=====================================

The version histories of all versionable nodes are stored in a repository-wide version store configured in the 
Versioning element of the repository configuration. The versioning configuration is much like workspace configuration 
as they are both used by Jackrabbit for storing content. The main difference between versioning and workspace 
configuration is that no search index is specified for the version store as version histories are indexed and searched 
using the repository-wide search index. Another difference is that there are no ```${wsp.name}``` or ```${wsp.path}```
variables for the versioning configuration. Instead the native file system path of the version store is explicitly 
specified in the configuration.

The structure of the versioning configuration is:

```xml
<Versioning rootPath="${rep.home}/version">
  <FileSystem .../>
  <PersistenceManager .../>
  <ISMLocking .../>           <!-- optional, available since 1.4 -->
</Versioning>
```

The versioning configuration elements are:

- ```FileSystem```: The virtual file system passed to the persistence manager.
- ```PersistenceManager```: Persistence configuration for the version store.
- ```ISMLocking```: Locking configuration for concurrent access to workspace content.