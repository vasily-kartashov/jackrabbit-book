Using references
====================================

The properties of type```PATH```, ```REFERENCE``` or ```WEAKREFERENCE``` let you reference other nodes / properties in 
the same workspace. A ```PATH``` can point to a node or a property while ```REFERENCE``` and ```WEAKREFERENCE``` can 
only point to a referenceable node (that is a node which has the mix:referenceable mixin). ```REFERENCE``` properties 
can only point to an existing node, while ```PATH``` and ```WEAKREFERENCE``` can point to nirvana. You can dereference 
these properties either manually or thought convenience methods.

```java
Node country = session.getRootNode().addNode("australia");
country.setProperty("name", "Australia");

Node canberra = country.addNode("canberra");
canberra.addMixin("mix:referenceable");
canberra.setProperty("name", "Canberra");
canberra.setProperty("population", 381488L);

Node sydney = country.addNode("sydney");
sydney.addMixin("mix:referenceable");
sydney.setProperty("name", "Sydney");
sydney.setProperty("population", 4757083L);

Node melbourne = country.addNode("melbourne");
melbourne.addMixin("mix:referenceable");
melbourne.setProperty("name", "Melbourne");
melbourne.setProperty("population", 4347955L);

country.setProperty("nsw-capital-population", "/australia/sydney/population", 
    PropertyType.PATH);
country.setProperty("capital", canberra.getIdentifier(), 
    PropertyType.WEAKREFERENCE);
country.setProperty("victoria-capital", melbourne);

String populationPropertyPath = country
    .getProperty("nsw-capital-population").getString();
assert session
    .getProperty(populationPropertyPath).getLong() == 4757083L;

String capitalIdentifier = country
    .getProperty("capital").getValue().getString();
assert session.getNodeByIdentifier(capitalIdentifier)
    .getProperty("name").getString().equals("Canberra");

String victoriaCapitalIdentifier = country
    .getProperty("victoria-capital").getString();
assert session.getNodeByIdentifier(victoriaCapitalIdentifier)
    .getProperty("name").getString().equals("Melbourne");

assert country.getProperty("nsw-capital-population")
    .getProperty().getLong() == 4757083L;
assert country.getProperty("capital").getNode()
    .getProperty("name").getString().equals("Canberra");
assert country.getProperty("victoria-capital").getNode()
    .getProperty("name").getString().equals("Melbourne");

PropertyIterator iterator1 = canberra.getWeakReferences();
while (iterator1.hasNext()) {
    Property property = iterator1.nextProperty();
    assert property.getParent().getProperty("name")
        .getString().equals("Australia");
}

PropertyIterator iterator2 = melbourne.getReferences();
while (iterator2.hasNext()) {
    Property property = iterator2.nextProperty();
    assert property.getParent().getProperty("name")
        .getString().equals("Australia");
}

sydney.remove();
canberra.remove();
```