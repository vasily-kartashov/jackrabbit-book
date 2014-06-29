Creating nodes and properties
=========================================

@todo write some stuff on schemaless and all

@todo you need to get the session object first.

```java
Node n0 = session.getRootNode().addNode("message");
n0.setProperty("content", "Hey, there!");
session.save();

Node n1 = session.getNode("/message");
assert n1.getProperty("content").getString().equals("Hey, there!");
```
