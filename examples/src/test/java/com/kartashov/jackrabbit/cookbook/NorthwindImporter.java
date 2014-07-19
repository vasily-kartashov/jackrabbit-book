package com.kartashov.jackrabbit.cookbook;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import org.apache.jackrabbit.commons.cnd.CndImporter;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class NorthwindImporter {

    private ValueFactory valueFactory;

    public void importData(Session session) throws Exception
    {
        valueFactory = session.getValueFactory();

        File cndFile = new File(getClass().getResource("/northwind.cnd").toURI());
        CndImporter.registerNodeTypes(new FileReader(cndFile), session);

        File accessFile = new File(getClass().getResource("/northwind.accdb").toURI());
        Database db = DatabaseBuilder.open(accessFile);

        Node territoriesNode = session.getRootNode().addNode("territories", "nw:territories");

        Map<Integer, Node> regionsMap = new HashMap<Integer, Node>();
        for (Row regionRow : db.getTable("Region")) {
            Integer regionId = (Integer) regionRow.get("RegionID");
            Node regionNode = territoriesNode.addNode(regionId.toString(), "nw:region");
            setValueProperty(regionRow, regionNode, "RegionDescription", "regionDescription");
            regionsMap.put(regionId, regionNode);
        }

        Map<String, Node> territoriesMap = new HashMap<String, Node>();
        for (Row territoryRow : db.getTable("Territories")) {
            String territoryId = ((String) territoryRow.get("TerritoryID")).trim();
            Integer regionId = (Integer) territoryRow.get("RegionID");

            Node regionNode = regionsMap.get(regionId);
            Node territoryNode = regionNode.addNode(territoryId, "nw:territory");
            setValueProperty(territoryRow, territoryNode, "TerritoryDescription", "territoryDescription");
            territoriesMap.put(territoryId, territoryNode);
        }


        Node employeesNode = session.getRootNode().addNode("employees", "nw:employees");

        Map<Integer, Node> employeesMap = new HashMap<Integer, Node>();
        Map<Integer, Integer> reportingMap = new HashMap<Integer, Integer>();
        for (Row employeeRow : db.getTable("Employees")) {
            Integer employeeId = (Integer) employeeRow.get("EmployeeID");
            Integer reportsTo = (Integer) employeeRow.get("ReportsTo");

            if (reportsTo != null) {
                reportingMap.put(employeeId, reportsTo);
            }

            Node employeeNode = employeesNode.addNode(employeeId.toString(), "nw:employee");
            setValueProperty(employeeRow, employeeNode, "LastName", "lastName");
            setValueProperty(employeeRow, employeeNode, "FirstName", "firstName");
            setValueProperty(employeeRow, employeeNode, "Title", "title");
            setValueProperty(employeeRow, employeeNode, "TitleOfCourtesy", "titleOfCourtesy");
            setValueProperty(employeeRow, employeeNode, "BirthDate", "birthDate");
            setValueProperty(employeeRow, employeeNode, "HireDate", "hireDate");
            setValueProperty(employeeRow, employeeNode, "Address", "address");
            setValueProperty(employeeRow, employeeNode, "City", "city");
            setValueProperty(employeeRow, employeeNode, "Region", "region");
            setValueProperty(employeeRow, employeeNode, "PostalCode", "postalCode");
            setValueProperty(employeeRow, employeeNode, "Country", "country");
            setValueProperty(employeeRow, employeeNode, "HomePhone", "homePhone");
            setValueProperty(employeeRow, employeeNode, "Extension", "extension");
            setValueProperty(employeeRow, employeeNode, "Photo", "photo");
            setValueProperty(employeeRow, employeeNode, "Notes", "notes");
            setValueProperty(employeeRow, employeeNode, "PhotoPath", "photoPath");
            employeesMap.put(employeeId, employeeNode);
        }

        for (Integer employeeId : reportingMap.keySet()) {
            Node employeeNode = employeesMap.get(employeeId);
            Integer reportsTo = reportingMap.get(employeeId);
            Node reportsToNode = employeesMap.get(reportsTo);
            employeeNode.setProperty("reportsTo", reportsToNode);
        }

        Map<Integer, List<String>> employeeTerritoriesMap = new HashMap<Integer, List<String>>();
        for (Row employeeTerritoryRow : db.getTable("EmployeeTerritories")) {
            Integer employeeId = (Integer) employeeTerritoryRow.get("EmployeeID");
            String territoryId = ((String) employeeTerritoryRow.get("TerritoryID")).trim();
            if (!employeeTerritoriesMap.containsKey(employeeId)) {
                employeeTerritoriesMap.put(employeeId, new ArrayList<String>());
            }
            employeeTerritoriesMap.get(employeeId).add(territoryId);
        }

        ValueFactory valueFactory = session.getValueFactory();
        for (Integer employeeId : employeeTerritoriesMap.keySet()) {
            List<String> territoryIds = employeeTerritoriesMap.get(employeeId);
            Value[] values = new Value[territoryIds.size()];
            for (int i = 0; i < values.length; i++) {
                String territoryId = territoryIds.get(i);
                values[i] = valueFactory.createValue(territoriesMap.get(territoryId));
            }
            employeesMap.get(employeeId).setProperty("territories", values);
        }

        Node productsNode = session.getRootNode().addNode("products", "nw:products");

        Node shippersNode = productsNode.addNode("shippers", "nw:shippers");
        Map<Integer, Node> shippersMap = new HashMap<Integer, Node>();
        for (Row shipperRow : db.getTable("Shippers")) {
            Integer shipperId = (Integer) shipperRow.get("ShipperID");
            Node shipperNode = shippersNode.addNode(shipperId.toString(), "nw:shipper");
            setValueProperty(shipperRow, shipperNode, "CompanyName", "companyName");
            setValueProperty(shipperRow, shipperNode, "Phone", "phone");
            shippersMap.put(shipperId, shipperNode);
        }

        Node categoriesNode = productsNode.addNode("categories", "nw:categories");
        Map<Integer, Node> categoriesMap = new HashMap<Integer, Node>();
        for (Row categoryRow : db.getTable("Categories")) {
            Integer categoryId = (Integer) categoryRow.get("CategoryID");
            Node categoryNode = categoriesNode.addNode(categoryId.toString(), "nw:category");
            setValueProperty(categoryRow, categoryNode, "CategoryName", "categoryName");
            setValueProperty(categoryRow, categoryNode, "Description", "description");
            setValueProperty(categoryRow, categoryNode, "Picture", "picture");
            categoriesMap.put(categoryId, categoryNode);
        }

        Node suppliersNode = productsNode.addNode("suppliers", "nw:suppliers");
        Map<Integer, Node> suppliersMap = new HashMap<Integer, Node>();
        for (Row supplierRow : db.getTable("Suppliers")) {
            Integer supplierId = (Integer) supplierRow.get("SupplierID");
            Node supplierNode = suppliersNode.addNode(supplierId.toString(), "nw:supplier");
            setValueProperty(supplierRow, supplierNode, "CompanyName", "companyName");
            setValueProperty(supplierRow, supplierNode, "ContactName", "contactName");
            setValueProperty(supplierRow, supplierNode, "ContactTitle", "contactTitle");
            setValueProperty(supplierRow, supplierNode, "Address", "address");
            setValueProperty(supplierRow, supplierNode, "City", "city");
            setValueProperty(supplierRow, supplierNode, "Region", "region");
            setValueProperty(supplierRow, supplierNode, "PostalCode", "postalCode");
            setValueProperty(supplierRow, supplierNode, "Country", "country");
            setValueProperty(supplierRow, supplierNode, "Phone", "phone");
            setValueProperty(supplierRow, supplierNode, "Fax", "fax");
            setValueProperty(supplierRow, supplierNode, "HomePage", "homePage");
            suppliersMap.put(supplierId, supplierNode);
        }

        Map<Integer, Node> productsMap = new HashMap<Integer, Node>();
        for (Row productRow : db.getTable("Products")) {
            Integer productId = (Integer) productRow.get("ProductID");
            Integer supplierId = (Integer) productRow.get("SupplierID");
            Integer categoryId = (Integer) productRow.get("CategoryID");
            Node supplierNode = suppliersMap.get(supplierId);
            Node productNode = supplierNode.addNode(productId.toString(), "nw:product");
            Node categoryNode = categoriesMap.get(categoryId);
            productNode.setProperty("category", categoryNode);
            setValueProperty(productRow, productNode, "ProductName", "productName");
            setValueProperty(productRow, productNode, "QuantityPerUnit", "quantityPerUnit");
            setValueProperty(productRow, productNode, "UnitPrice", "unitPrice");
            setValueProperty(productRow, productNode, "UnitsInStock", "unitsInStock");
            setValueProperty(productRow, productNode, "UnitsInOrder", "unitsInOrder");
            setValueProperty(productRow, productNode, "ReorderLevel", "reorderLevel");
            setValueProperty(productRow, productNode, "Discontinued", "discontinued");
            productsMap.put(productId, productNode);
        }

        Node customersNode = session.getRootNode().addNode("customers", "nw:customers");

        Map<String, Node> customersMap = new HashMap<String, Node>();
        for (Row customerRow : db.getTable("Customers")) {
            String customerId = ((String) customerRow.get("CustomerID")).trim();
            Node customerNode = customersNode.addNode(customerId, "nw:customer");
            setValueProperty(customerRow, customerNode, "CompanyName", "companyName");
            setValueProperty(customerRow, customerNode, "ContactName", "contactName");
            setValueProperty(customerRow, customerNode, "ContactTitle", "contactTitle");
            setValueProperty(customerRow, customerNode, "Address", "address");
            setValueProperty(customerRow, customerNode, "City", "city");
            setValueProperty(customerRow, customerNode, "Region", "region");
            setValueProperty(customerRow, customerNode, "PostalCode", "postalCode");
            setValueProperty(customerRow, customerNode, "Country", "country");
            setValueProperty(customerRow, customerNode, "Phone", "phone");
            setValueProperty(customerRow, customerNode, "Fax", "fax");
            customersMap.put(customerId, customerNode);
        }

        Node ordersNode = session.getRootNode().addNode("orders", "nw:orders");
        Map<Integer, Node> ordersMap = new HashMap<Integer, Node>();
        for (Row orderRow : db.getTable("Orders")) {
            Integer orderId = (Integer) orderRow.get("OrderID");
            Node orderNode = ordersNode.addNode(orderId.toString(), "nw:order");
            String customerId = ((String) orderRow.get("CustomerID")).trim();
            Integer employeeId = (Integer) orderRow.get("EmployeeID");
            Integer shipVia = (Integer) orderRow.get("ShipVia");

            orderNode.setProperty("customer", customersMap.get(customerId));
            orderNode.setProperty("employee", employeesMap.get(employeeId));
            orderNode.setProperty("shipVia", shippersMap.get(shipVia));

            setValueProperty(orderRow, orderNode, "OrderDate", "orderDate");
            setValueProperty(orderRow, orderNode, "RequiredDate", "requiredDate");
            setValueProperty(orderRow, orderNode, "ShippedDate", "shippedDate");
            setValueProperty(orderRow, orderNode, "Freight", "freight");
            setValueProperty(orderRow, orderNode, "ShipName", "shipName");
            setValueProperty(orderRow, orderNode, "ShipAddress", "shipAddress");
            setValueProperty(orderRow, orderNode, "ShipCity", "shipCity");
            setValueProperty(orderRow, orderNode, "ShipRegion", "shipRegion");
            setValueProperty(orderRow, orderNode, "ShipPostalCode", "shipPostalCode");
            setValueProperty(orderRow, orderNode, "ShipCountry", "shipCountry");

            ordersMap.put(orderId, orderNode);
        }

        for (Row detailsRow : db.getTable("OrderDetails")) {
            Integer orderId = (Integer) detailsRow.get("OrderID");
            Integer productId = (Integer) detailsRow.get("ProductID");
            Node orderNode = ordersMap.get(orderId);
            Long index = orderNode.getNodes().getSize() + 1;
            Node detailsNode = orderNode.addNode(index.toString(), "nw:detail");
            detailsNode.setProperty("product", productsMap.get(productId));
            setValueProperty(detailsRow, detailsNode, "UnitPrice", "unitPrice");
            setValueProperty(detailsRow, detailsNode, "Quantity", "quantity");
            setValueProperty(detailsRow, detailsNode, "Discount", "discount");
        }

        session.save();
    }

    private void setValueProperty(Row row, Node node, String columnName, String propertyName) throws Exception {
        Object value = row.get(columnName);
        if (value == null) {
            return;
        }
        if (value instanceof Integer) {
            node.setProperty(propertyName, (Integer) value);
        } else if (value instanceof String) {
            node.setProperty(propertyName, ((String) value).trim());
        } else if (value instanceof Date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((Date) value);
            node.setProperty(propertyName, calendar);
        } else if (value instanceof byte[]) {
            node.setProperty(propertyName, valueFactory.createBinary(new ByteArrayInputStream((byte[]) value)));
        } else if (value instanceof BigDecimal) {
            node.setProperty(propertyName, ((BigDecimal) value).doubleValue());
        } else if (value instanceof Short) {
            node.setProperty(propertyName, (Short) value);
        } else if (value instanceof Boolean) {
            node.setProperty(propertyName, (Boolean) value);
        } else if (value instanceof Float) {
            node.setProperty(propertyName, (Float) value);
        } else {
            throw new Exception("Unknown column type " + value.getClass());
        }
    }
}
