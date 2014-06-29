Structure of repository.xml
==========================================

@todo maybe adding DTD would be more helpful?

```xml
<Repository>
    <FileSystem .../>
    <Security .../>
    <Workspaces .../>
    <Workspace .../>
    <Versioning .../>
    <SearchIndex .../>    <!-- optional -->
    <Cluster .../>        <!-- optional, available since 1.2 -->
    <DataStore .../>      <!-- optional, available since 1.4 -->
</Repository>
```