Working with node versions
==================================

First you need to fetch version manager, which is always associated with your current workspace
```java
VersionManager versionManager = session.getWorkspace().getVersionManager();
```

Let's create top level node ```message``` (@todo why unstructured???) and add mixin class ```mix:versionable```. You can 
see this mixin as a marker that specifies that this node is versionable and therefore has additional properties to 
support versioning. We then proceed by adding a property ```content``` to this node.
```java
Node n0 = session.getRootNode().addNode("message", 
    JcrConstants.NT_UNSTRUCTURED);
n0.addMixin(JcrConstants.MIX_VERSIONABLE);
n0.setProperty("content", "Hello");
session.save();
```

@todo explain very clearly what checkin and checkout really do
```java
Version firstVersion = versionManager.checkin(n0.getPath());
```

```java
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
```