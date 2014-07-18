package com.kartashov.jackrabbit.cookbook;

import com.healthmarketscience.jackcess.*;
import org.apache.jackrabbit.commons.cnd.CndImporter;

import javax.jcr.Node;
import javax.jcr.Session;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NorthwindImporter {

    public void importData(Session session) throws Exception
    {
        File cndFile = new File(getClass().getResource("/northwind.cnd").toURI());
        CndImporter.registerNodeTypes(new FileReader(cndFile), session);

        File accessFile = new File(getClass().getResource("/northwind.accdb").toURI());
        Database db = DatabaseBuilder.open(accessFile);

        Node territoriesNode = session.getRootNode().addNode("territories", "nw:territories");

        Map<Integer, Node> regionsMap = new HashMap<Integer, Node>();
        for (Row regionRow : db.getTable("Region")) {
            Integer regionId = (Integer) regionRow.get("RegionID");
            String regionDescription = ((String) regionRow.get("RegionDescription")).trim();

            Node regionNode = territoriesNode.addNode(regionId.toString(), "nw:region");
            regionNode.setProperty("regionDescription", regionDescription);
            regionsMap.put(regionId, regionNode);
        }

        Map<String, Node> territoriesMap = new HashMap<String, Node>();
        for (Row territoryRow : db.getTable("Territories")) {
            String territoryId = ((String) territoryRow.get("TerritoryID")).trim();
            String territoryDescription = ((String) territoryRow.get("TerritoryDescription")).trim();
            Integer regionId = (Integer) territoryRow.get("RegionID");

            Node regionNode = regionsMap.get(regionId);
            Node territoryNode = regionNode.addNode(territoryId, "nw:territory");
            territoryNode.setProperty("territoryDescription", territoryDescription);
            territoriesMap.put(territoryId, territoryNode);
        }

        Node employeesNode = session.getRootNode().addNode("employees", "nw:employees");

        Map<Integer, Node> employeesMap = new HashMap<Integer, Node>();
        for (Row employeeRow : db.getTable("Employees")) {
            Integer employeeId = (Integer) employeeRow.get("EmployeeID");
            String lastName = ((String) employeeRow.get("LastName")).trim();
            String firstName = ((String) employeeRow.get("FirstName")).trim();
            String title = ((String) employeeRow.get("Title")).trim();
            String titleOfCourtesy = ((String) employeeRow.get("TitleOfCourtesy")).trim();
            Date birthDate = (Date) employeeRow.get("BirthDate");
            Date hireDate = (Date) employeeRow.get("HireDate");
            String address = ((String) employeeRow.get("Address")).trim();
            String city = ((String) employeeRow.get("City")).trim();
            // String region = ((String) employeeRow.get("Region")).trim();
            String postalCode = ((String) employeeRow.get("PostalCode")).trim();
            String country = ((String) employeeRow.get("Country")).trim();
            String homePhone = ((String) employeeRow.get("HomePhone")).trim();
            String extension = ((String) employeeRow.get("Extension")).trim();
            // photo?
            String notes = ((String) employeeRow.get("Notes")).trim();
            // reports to?
            String photoPath = ((String) employeeRow.get("PhotoPath")).trim();

        }

        Node productsNode = session.getRootNode().addNode("products", "nw:products");

        Node customersNode = session.getRootNode().addNode("customers", "nw:customers");

        Node ordersNode = session.getRootNode().addNode("orders", "nw:orders");

        session.save();
    }
}
