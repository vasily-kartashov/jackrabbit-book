Exporting and importing subgraphs
======================================

@todo Export binary data and links to show how they're serialized

```java
session.getRootNode().addNode("message");
session.save();

File file = File.createTempFile("jcr", ".xml");

BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
session.exportSystemView("/message", out, false, false);
out.close();

session.removeItem("/message");
session.save();
assert !session.nodeExists("/node-01-06");

BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
session.getWorkspace().importXML("/", in, 
    ImportUUIDBehavior.IMPORT_UUID_COLLISION_REMOVE_EXISTING);
in.close();
file.delete();

assert session.nodeExists("/message");
```