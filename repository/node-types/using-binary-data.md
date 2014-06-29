Using binary data
===========================================

If you want to reuse the built-in ```nt:file``` type, you will have to create a child node of type ```jcr:content```. 
The binary data is written into the property ```jcr:data```. You may also want to specify the mime-type of the content 
by setting ```jcr:mimeType``` property as JCR has no way to infer this information.

@ToDo what about dispose???

```java
ValueFactory valueFactory = session.getValueFactory();

Node n0 = session.getRootNode().addNode("image", JcrConstants.NT_FILE);
Node n1 = n0.addNode(JcrConstants.JCR_CONTENT, JcrConstants.NT_RESOURCE);

File file = new File(getClass().getResource("/image.png").toURI());
n1.setProperty(JcrConstants.JCR_DATA, 
    valueFactory.createBinary(new FileInputStream(file)));
n1.setProperty(JcrConstants.JCR_MIMETYPE, "image/png");
n1.setProperty(JcrConstants.JCR_LASTMODIFIED, 
    Calendar.getInstance().getTimeInMillis());

assert file.length() == session.getProperty("/image/jcr:content/jcr:data")
    .getBinary().getSize();
```