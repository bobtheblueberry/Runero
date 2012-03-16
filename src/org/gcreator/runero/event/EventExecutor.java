package org.gcreator.runero.event;

import java.util.List;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.inst.RoomInstance;

public class EventExecutor {

    public static void executeEvent(List<Event> el, RoomInstance room) {
        for (Event e : el)
            for (ObjectGroup g : room.instanceGroups)
                if (g.obj.getId() == e.object.getId())
                    for (Instance i : g.instances)
                        i.performEvent(e);

    }

    /**
     * Executes an event for all the correct instances in the room
     * 
     * @param e
     * @param room
     */
    public static void executeEvent(Event e, RoomInstance room) {
        ObjectGroup g = room.getObjectGroup2(e.object.getId());
        if (g == null) // No instances
            return;
        for (Instance i : g.instances)
            i.performEvent(e);
    }
}
