Property types
======================================

Every property must have one of the following types:
- ```STRING``` - ```java.lang.String``` object.
- ```URI``` - ```java.lang.String``` object that confirms to the syntax of an URI-reference as defined in RFC 3986.
- ```BOOLEAN``` - ```boolean``` value.
- ```LONG``` - ```long``` value.
- ```DOUBLE``` - ```double``` object.
- ```DECIMAL``` - ```java.math.BigDecimal``` object.
- ```BINARY``` - ```javax.jcr.Binary``` object.
- ```DATE``` - ```java.util.Calendar``` object.
- ```NAME``` - ???.
- ```PATH``` - ```java.lang.String???``` object that serves as a pointer to location within workspace. Please note that
  the properties of type ```PATH``` don't have to point to existing object nor prevent you from deleting or moving the
  referenced location.
- ```WEAKREFERENCE``` - ```java.lang.String???``` object storing the ID of the referenced node(s). You can still move
  and delete referenced node.
- ```REFERENCE``` - the same as ```WEAKREFERENCE``` except the ```REFERENCE``` always have to point to an existing node.

```java
// @todo write code that shows how to work with properties
```
