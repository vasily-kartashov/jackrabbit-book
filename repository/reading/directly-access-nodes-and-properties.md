Directly access nodes and properties
==============================================

@todo primary item what is it good for? 
@todo Node and property with the same name? 
@todo no rights to read the node 
@todo JcrUtils (definitely worth looking at)

```java
Node australia = session.getRootNode().addNode("australia");
Node canberra = australia.addNode("canberra");
canberra.setProperty("capital", true);
Node sydney = australia.addNode("sydney");
String sydneyId = sydney.getIdentifier();
sydney.remove();
Node melbourne = australia.addNode("melbourne");
Node perth = australia.addNode("perth");
Node darwin = australia.addNode("darwin");
Node brisbane = australia.addNode("brisbane");
Node adelaide = australia.addNode("adelaide");
session.save();

assert session.getItem("/australia/canberra").isNode();
assert !session.getItem("/australia/canberra/capital").isNode();

boolean exceptionThrown = false;

try {
    session.getItem("/australia/sydney");
    exceptionThrown = false;
} catch (PathNotFoundException e) {
    exceptionThrown = true;
}
assert exceptionThrown;

assert session.itemExists("/australia/canberra");
assert session.itemExists("/australia/canberra/capital");

assert !session.propertyExists("/australia/canberra");
assert session.propertyExists("/australia/canberra/capital");

assert session.getNode("/australia/canberra").isNode();
try {
    session.getNode("/australia/canberra/capital");
    exceptionThrown = false;
} catch (PathNotFoundException e) {
    exceptionThrown = true;
}
assert exceptionThrown;

assert session.nodeExists("/australia/canberra");
assert !session.nodeExists("/australia/sydney");
assert !session.nodeExists("/australia/canberra/capital");

assert session.getNodeByIdentifier(australia.getIdentifier()).getPath().equals(australia.getPath());
try {
    session.getNodeByIdentifier(sydneyId);
    exceptionThrown = false;
} catch (ItemNotFoundException e) {
    exceptionThrown = true;
}
assert exceptionThrown;
assert session.getNode("[" + perth.getIdentifier() + "]").getPath().equals(perth.getPath());

assert australia.getNode("adelaide").getIdentifier().equals(adelaide.getIdentifier());
assert melbourne.getNode("../canberra").getIdentifier().equals(canberra.getIdentifier());
assert perth.hasNode("../darwin");
assert perth.hasProperty("../canberra/capital");
assert perth.getProperty("../canberra/capital").getBoolean();
```