Browsing repository through WebDAV
=======================================================

https://mail-archives.apache.org/mod_mbox/jackrabbit-users/201312.mbox/%3CA31DF5AB-D613-4225-B458-CE4A07BBC786%40rvt.dds.nl%3E http://jackrabbit.apache.org/api/1.4/org/apache/jackrabbit/webdav/jcr/JCRWebdavServerServlet.html (see how to do that?)

You can enable WebDav interface for your repository. For example you can use the following two dependencies to your web project
```xml
<dependency>
    <groupId>org.apache.jackrabbit</groupId>
    <artifactId>jackrabbit-webdav</artifactId>
    <version>${version.jackrabbit}</version>
</dependency>
<dependency>
    <groupId>org.apache.jackrabbit</groupId>
    <artifactId>jackrabbit-jcr-server</artifactId>
    <version>${version.jackrabbit}</version>
</dependency>
```
Extend the SimpleWebdavServlet provided by the new dependencies to initialize the repository. For example like this

```java
public class WebdavServlet extends SimpleWebdavServlet {

    private static Repository repository;

    static {
        repository = ...
    }

    @Override
    public Repository getRepository() {
        return repository;
    }
}
```

Add the servlet mapping for example like this:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app
        version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
            http://java.sun.com/xml/ns/javaee 
            http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <servlet>
        <servlet-name>jackrabbit</servlet-name>
        <servlet-class>...WebdavServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>jackrabbit</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

After that you can start your favourite WebDav client and browse the content of the repository.