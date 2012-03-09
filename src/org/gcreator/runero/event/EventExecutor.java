package org.gcreator.runero.event;

import java.util.List;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.inst.RoomInstance;

public class EventExecutor {

    public static void executeEvent(List<Event> el, RoomInstance room) {
        for (Event e : el) {
            for (ObjectGroup g : room.instanceGroups)
                if (g.obj.getId() == g.obj.getId()) {
                    for (Instance i : g.instances)
                        i.performEvent(e);
                }
        }
    }
}
