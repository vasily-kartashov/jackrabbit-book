Workspaces
=================================

A Jackrabbit repository contains one or more workspaces that are each configured in a separate ```workspace.xml``` 
configuration file. The Workspaces element of the repository configuration specifies where and how the workspaces are 
managed. The repository configuration also contains a default workspace configuration template that is used to create 
the ```workspace.xml``` file of a new workspace unless more specific configuration is given when the workspace is 
created. See the ```createWorkspace``` methods in the ```JackrabbitWorkspace``` interface for more details on workspace 
creating workspaces.

The workspace settings in the repository configuration file are:

```xml
<Workspaces rootPath="${rep.home}/workspaces"
            defaultWorkspace="default"
            configRootPath="..."  <!-- optional -->
            maxIdleTime="..."/>   <!-- optional -->

<Workspace .../>   <!-- default workspace configuration template -->
```

The following global workspace configuration options are specified in the Workspaces element:

- ```rootPath```: The native file system directory for workspaces. A subdirectory is automatically created for each 
  workspace, and the path of that subdirectory can be used in the workspace configuration as the ```${wsp.path}``` 
  variable.
- ```defaultWorkspace```: Name of the default workspace. This workspace is automatically created when the repository is 
  first started.
- ```configRootPath```: By default the configuration of each workspace is stored in a ```workspace.xml``` file within 
  the workspace directory within the rootPath directory. If this option is specified, then the workspace configuration 
  files are stored within the specified path in the virtual file system (see above) configured for the repository.
- ```maxIdleTime```: By default Jackrabbit only releases resources associated with an opened workspace when the entire 
  repository is closed. This option, if specified, sets the maximum number of seconds that a workspace can remain unused 
  before the workspace is automatically closed. The workspace configuration template and all ```workspace.xml```
  configuration files have the following structure:

```xml
<Workspace name="${wsp.name}">
  <FileSystem .../>
  <PersistenceManager .../>
  <SearchIndex .../>          <!-- optional -->
  <ISMLocking .../>           <!-- optional, available since 1.4 -->
</Workspace>
```

The workspace configuration elements are:
- ```FileSystem```: The virtual file system passed to the persistence manager and search index.
- ```PersistenceManager```: Persistence configuration for workspace content.
- ```SearchIndex```: Configuration of the workspace search index.
- ```ISMLocking```: Locking configuration for concurrent access to workspace content.

To modify the configuration of an existing workspace, you need to change the ```workspace.xml``` file of that workspace. 
Changing the ```<Workspace/>``` element in the repository configuration file will not affect existing workspaces.