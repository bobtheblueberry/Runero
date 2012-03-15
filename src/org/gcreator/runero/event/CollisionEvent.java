package org.gcreator.runero.event;

import org.gcreator.runero.inst.Instance;

/**
 * Collision event
 * 
 * Event, GameObject to collide with
 * 
 * 
 * @author serge
 *
 */
public class CollisionEvent implements Comparable<CollisionEvent> {

    public Event event;
    public int otherId;
    
    public CollisionEvent(Event e, int other) {
        this.event = e;
        this.otherId = other;
    }

    /**
     * Performs collision event
     */
    public void collide(Instance instance, Instance other) {
        event.collisionObject = other;
        instance.performEvent(event);
    }
    
    @Override
    public int compareTo(CollisionEvent o) {
        return event.object.compareTo(o.event.object);
    }
}
