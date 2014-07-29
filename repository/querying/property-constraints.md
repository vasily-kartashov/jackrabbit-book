Property constraints
=====

This is how you check that item property is missing

```java
String expression = "SELECT * FROM [nw:employee] WHERE [region] IS NULL";
QueryManager queryManager = session.getWorkspace().getQueryManager();
Query query = queryManager.createQuery(expression, Query.JCR_SQL2);

QueryResult result = query.execute();
for (Row row : JcrUtils.getRows(result)) {
    Node employee = row.getNode("nw:employee");
    String firstName = employee.getProperty("firstName").getString();
    String lastName = employee.getProperty("lastName").getString();
    System.out.println("Employee without region: " + firstName + " " + lastName);
}
```

Test criteria on multivalued property

```java

```




- how to test the number of values for multivalued
- how to embed the value
- how to add criteria to the query
- how to use like



