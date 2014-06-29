package com.kartashov.jackrabbit.cookbook;

import org.apache.jackrabbit.spi.Event;
import org.junit.Test;

import javax.jcr.RepositoryException;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

public class ObservationTest extends AbstractTest {

    @Test
    public void observe() throws RepositoryException {

        ObservationManager observationManager = session.getWorkspace().getObservationManager();
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
        observationManager.addEventListener(eventListener, Event.ALL_TYPES, "/", true, null, null, false);

        session.getRootNode().addNode("node");
        session.save();
    }
}
