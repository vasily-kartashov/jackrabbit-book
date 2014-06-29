Declaring node types with XML
=============================================

The same configuration is possible with XML, which is comparing to CND somewhat verbose. You may want to stick to XML 
configuration if your system expects you to, and if you want to supported by your IDE while writing the configuration.

```xml
<?xml version="1.0"?>
<nodeTypes
        xmlns:nt="http://www.jcp.org/jcr/nt/1.0"
        xmlns:mix="http://www.jcp.org/jcr/mix/1.0"
        xmlns:app="http://kartashov.com/jackrabbit-cookbook">
    <nodeType name="app:country" isMixin="false" hasOrderableChildNodes="true" primaryItemName="">
        <supertypes>
            <supertype>nt:base</supertype>
        </supertypes>
        <propertyDefinition name="app:name" requiredType="String" autoCreated="true" mandatory="true"
                            onParentVersion="COPY" multiple="false" protected="false">
            <defaultValues>
                <defaultValue><![CDATA[]]></defaultValue>
            </defaultValues>
        </propertyDefinition>
        <propertyDefinition name="app:capital" requiredType="WeakReference" autoCreated="false" mandatory="false"
                            onParentVersion="COPY" multiple="false" protected="false"/>
        <childNodeDefinition name="*" defaultPrimaryType="app:city" autoCreated="false" mandatory="false"
                             onParentVersion="VERSION" protected="false" sameNameSiblings="true">
            <requiredPrimaryTypes>
                <requiredPrimaryType>app:city</requiredPrimaryType>
            </requiredPrimaryTypes>
        </childNodeDefinition>
    </nodeType>
    <nodeType name="app:city" isMixin="false" hasOrderableChildNodes="false" primaryItemName="">
        <supertypes>
            <supertype>nt:base</supertype>
            <supertype>mix:referenceable</supertype>
        </supertypes>
        <propertyDefinition name="app:name" requiredType="String" autoCreated="true" mandatory="true"
                            onParentVersion="COPY" multiple="false" protected="false">
            <defaultValues>
                <defaultValue><![CDATA[]]></defaultValue>
            </defaultValues>
        </propertyDefinition>
        <propertyDefinition name="app:population" requiredType="Long" autoCreated="true" mandatory="true"
                            onParentVersion="COPY" multiple="false" protected="false">
            <defaultValues>
                <defaultValue>0</defaultValue>
            </defaultValues>
        </propertyDefinition>
        <childNodeDefinitions/>
    </nodeType>
</nodeTypes>
```

```java
JackrabbitNodeTypeManager nodeTypeManager = (JackrabbitNodeTypeManager) session.getWorkspace().getNodeTypeManager();
File file = file = new File(getClass().getResource("/node-types.xml").toURI());
nodeTypeManager.registerNodeTypes(new FileInputStream(file), JackrabbitNodeTypeManager.TEXT_XML);

Node n0 = session.getRootNode().addNode("australia", "app:country");

assert n0.hasProperty("app:name");

Node n1 = n0.addNode("canberra", "app:city");

assert n1.hasProperty("app:name");
assert n1.hasProperty("app:population");

n0.setProperty("app:capital", n1);
```

Please note that the ```JackrabbitNodeTypeManager``` is deprecated in favor of JCR conform ```NodeTypeManager```.