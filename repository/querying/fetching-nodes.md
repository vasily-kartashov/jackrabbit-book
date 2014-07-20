Fetching nodes
==============================================

Fetching all employees

```java
String expression = "SELECT [firstName], [lastName] FROM [nw:employee] ORDER BY [firstName], [lastName]";
QueryManager queryManager = session.getWorkspace().getQueryManager();
Query query = queryManager.createQuery(expression, Query.JCR_SQL2);

QueryResult result = query.execute();
for (Row row : JcrUtils.getRows(result)) {
    Node node = row.getNode("nw:employee");
    String firstName = node.getProperty("firstName").getString();
    String lastName = node.getProperty("lastName").getString();
    System.out.println(firstName + " " + lastName);
}
```

Lets fetch all employees grouped by territory

```java
String expression =
        "SELECT * " +
        "FROM [nw:territory] " +
        "RIGHT OUTER JOIN [nw:employee] " +
        "ON [nw:territory].[jcr:uuid] = [nw:employee].[territories]" +
        "ORDER BY [nw:territory].[territoryDescription]";
QueryManager queryManager = session.getWorkspace().getQueryManager();
Query query = queryManager.createQuery(expression, Query.JCR_SQL2);

QueryResult result = query.execute();
for (Row row : JcrUtils.getRows(result)) {
    Node territory = row.getNode("nw:territory");
    Node employee = row.getNode("nw:employee");

    String territoryDescription = territory.getProperty("territoryDescription").getString();
    String firstName = employee.getProperty("firstName").getString();
    String lastName = employee.getProperty("lastName").getString();
    System.out.println(territoryDescription + ": " + firstName + " " + lastName);
}
```

Todo
- Fetch nodes without classes
- Group & math like size of an order
- Group by like group all customers by country
- All types of join available (for joins section !)
- Using versioning in the selection
- How does locking affect querying
- Full text fetch (for full test search section!)
- Conditions on attributes (multi-value, references, sub-queries, ranges, all for conditions section!)
- 