package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.commons.JcrUtils;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;


public class QueryingTest extends AbstractTest {

    @Before
    public void populateRepository() throws Exception {
        NorthwindImporter importer = new NorthwindImporter();
        importer.importData(session);
    }

    @Test
    public void testFetchEmployeeNames() throws Exception {
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
    }

    @Test
    public void testFetchEmployeesGroupedByTerritory() throws Exception {
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
    }

}
