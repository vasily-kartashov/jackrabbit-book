Using multivalued properties
===================================

The only way to work with multivalued properties is to fetch the array of values, modify it and overwrite the old value. 
There are no(!) convenience methods like ```addValue``` or ```indexOf```.

```java
ValueFactory valueFactory = session.getValueFactory();

Node n0 = session.getRootNode().addNode("australia");
n0.setProperty("states", new Value[]{
        valueFactory.createValue("New South Wales"),
        valueFactory.createValue("Queensland"),
        valueFactory.createValue("South Australia"),
        valueFactory.createValue("Tasmania"),
        valueFactory.createValue("Victoria"),
        valueFactory.createValue("Western Australia")
});

Value[] s0 = n0.getProperty("states").getValues();
Value[] s1 = Arrays.copyOf(s0, s0.length + 1);
s1[s1.length - 1] = valueFactory.createValue("Australia Capital Territory");
n0.setProperty("states", s1);

assert n0.getProperty("states").getValues().length == 7;
```