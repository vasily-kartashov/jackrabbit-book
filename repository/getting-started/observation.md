Observation
=========================================

Jackrabbit supports observations which enables an application to receive notifications on changes to a workspace.

@todo check if the event is triggered on save or something else

```java
ObservationManager observationManager = session.getWorkspace()
    .getObservationManager();
    
EventListener eventListener = new EventListener() {
    @Override
    public void onEvent(EventIterator events) {
        try {
            assert events.nextEvent().getPath().equals("/nodes");
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
};
observationManager.addEventListener(eventListener, Event.ALL_TYPES, "/", 
    true, null, null, false);

session.getRootNode().addNode("node");
session.save();
```