Working with shareable nodes
===========================================

You cannot clone the node so that the clone has the same parent as the original node. You don't need to save after 
cloning as the repository is automatically persisted.

Each node in shared set shares the same properties and their respective property values. When a change, addition or 
removal of a property of one node in a shared set is made, that change, addition or removal is immediately reflected in 
the properties of each node in the shared set.

Each node in a shared set shares the same child nodes. In particular, the addition or removal of a child from a shared 
node N automatically adds or removes that child from all the nodes in the shared set

```java
Workspace workspace = session.getWorkspace();

Node n0 = session.getRootNode().addNode("message");
n0.addMixin(JcrConstants.MIX_SHAREABLE);
n0.setProperty("content", "Hello");
session.getRootNode().addNode("parent");
session.save();

workspace.clone(workspace.getName(), "/message", "/parent/message-copy", false);

assert session.nodeExists("/parent/message-copy");
assert session.getNode("/parent/message-copy").getProperty("content")
    .getString().equals("Hello");

n0.setProperty("content", "Good bye");
session.save();

assert session.getNode("/parent/message-copy").getProperty("content")
    .getString().equals("Good bye");

n0.remove();
session.save();

assert !session.nodeExists("/message");
assert session.nodeExists("/parent/message-copy");
```