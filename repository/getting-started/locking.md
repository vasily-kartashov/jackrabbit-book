Locking
===================================

```java
Node n0 = session.getRootNode().addNode("node");
n0.addMixin(JcrConstants.MIX_LOCKABLE);
session.save();

LockManager lockManager = session.getWorkspace().getLockManager();
lockManager.lock("/node", true, false, 3600, "");

assert lockManager.isLocked("/node");

n0.setProperty("message", "I can do whatever I want"); // @todo create another session and try to change
session.save();

lockManager.unlock("/node");
assert !lockManager.isLocked("/node");
```