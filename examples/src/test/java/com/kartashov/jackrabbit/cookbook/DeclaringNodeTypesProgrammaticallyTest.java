package com.kartashov.jackrabbit.cookbook;

import org.junit.Test;

import javax.jcr.*;
import javax.jcr.nodetype.*;

public class DeclaringNodeTypesProgrammaticallyTest extends AbstractTest {

    @Test
    @SuppressWarnings("unchecked")
    public void declareNodeTypesProgrammatically() throws Exception {
        
        NamespaceRegistry namespaceRegistry = session.getWorkspace().getNamespaceRegistry();
        namespaceRegistry.registerNamespace("app", "http://kartashov.com/jackrabbit-cookbook");

        NodeTypeManager nodeTypeManager = session.getWorkspace().getNodeTypeManager();
        ValueFactory valueFactory = session.getValueFactory();

        PropertyDefinitionTemplate name = nodeTypeManager.createPropertyDefinitionTemplate();
        name.setName("app:name");
        name.setRequiredType(PropertyType.STRING);
        name.setDefaultValues(new Value[]{ valueFactory.createValue("") });
        name.setMandatory(true);
        name.setAutoCreated(true);

        PropertyDefinitionTemplate capital = nodeTypeManager.createPropertyDefinitionTemplate();
        capital.setName("app:capital");
        capital.setRequiredType(PropertyType.WEAKREFERENCE);
        capital.setMandatory(false);

        PropertyDefinitionTemplate population = nodeTypeManager.createPropertyDefinitionTemplate();
        population.setName("app:population");
        population.setRequiredType(PropertyType.LONG);
        population.setDefaultValues(new Value[]{ valueFactory.createValue(0L) });
        population.setMandatory(true);
        population.setAutoCreated(true);

        NodeTypeTemplate country = nodeTypeManager.createNodeTypeTemplate();
        country.setName("app:country");
        country.setOrderableChildNodes(true);
        country.setDeclaredSuperTypeNames(new String[]{
                "nt:base"
        });

        country.getPropertyDefinitionTemplates().add(capital);
        country.getPropertyDefinitionTemplates().add(name);

        NodeTypeTemplate city = nodeTypeManager.createNodeTypeTemplate();
        city.setName("app:city");
        city.setOrderableChildNodes(true);
        city.setDeclaredSuperTypeNames(new String[]{
                "nt:base",
                "mix:referenceable"
        });

        city.getPropertyDefinitionTemplates().add(name);
        city.getPropertyDefinitionTemplates().add(population);

        NodeDefinitionTemplate child = nodeTypeManager.createNodeDefinitionTemplate();
        child.setName("*");
        child.setDefaultPrimaryTypeName("app:city");
        child.setRequiredPrimaryTypeNames(new String[]{"app:city"});

        country.getNodeDefinitionTemplates().add(child);

        nodeTypeManager.registerNodeTypes(new NodeTypeDefinition[]{ country, city }, true);

        Node n0 = session.getRootNode().addNode("australia-03-03", "app:country");

        assert n0.hasProperty("app:name");

        Node n1 = n0.addNode("canberra-03-03", "app:city");

        assert n1.hasProperty("app:name");
        assert n1.hasProperty("app:population");

        n0.setProperty("app:capital", n1);
    }
}
