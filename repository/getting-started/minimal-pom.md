Minimal POM
=========================================

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
            http://maven.apache.org/POM/4.0.0 
            http://maven.apache.org/xsd/maven-4.0.0.xsd">
         
    <modelVersion>4.0.0</modelVersion>

    <groupId>example</groupId>
    <artifactId>example</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <version.jcr>[2.0, 3.0)</version.jcr>
        <version.jackrabbit>[2.8.0, 3.0.0)</version.jackrabbit>
        <version.jackrabbit-ocm>[2.0.0, 3.0.0)</version.jackrabbit-ocm>
        <version.junit>[4.10, 5.0)</version.junit>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>javax.jcr</groupId>
            <artifactId>jcr</artifactId>
            <version>${version.jcr}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.jackrabbit</groupId>
            <artifactId>jackrabbit-core</artifactId>
            <version>${version.jackrabbit}</version>
        </dependency>
        <dependency>
            <groupId>org.apache</groupId>
            <artifactId>jackrabbit-ocm</artifactId>
            <version>${version.jackrabbit-ocm}</version>
        </dependency>
    </dependencies>

</project>
```