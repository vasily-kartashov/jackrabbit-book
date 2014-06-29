Deleting nodes and properties
====================================================

Let's create node ```/message``` with property ```content```
```java
Node n0 = session.getRootNode().addNode("message");
n0.setProperty("content", "Hey, there!");
session.save();
```

Fetch the newly created node from the repository. Note that we fetch a copy of the object
```java
Node n1 = session.getNode("/message");
assert n0 != n1;
```

To remove the property set the value to ```null``` and save
```java
n1.setProperty("content", (String) null);
session.save();
```
        
Let's fetch another copy of the node ```/message```. Note that this is again a completely different address. The property 
has completely disappeared and not just set to ```null```
```java
Node n2 = session.getNode("/message");
assert n1 != n2;
assert !n2.hasProperty("content");
```

Let's not remove the node ```/message```. As long as the session is not saved the node is visible to other sessions 
but disappears from current one (@todo check if true). After we save the session, the node disappears
```java
session.removeItem("/message");
assert !session.itemExists("/message");
session.save();
assert !session.itemExists("/message");
```