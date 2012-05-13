package org.gcreator.runero.event;

import java.util.LinkedList;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.inst.RoomInstance;

/**
 * Used for instance create/destroy/change events
 * they cannot be added to the room during an event
 * because this causes Java to throw list errors
 * 
 * @author serge
 *
 */
public class EventQueue {
    static LinkedList<ChangeInstance> change;
    static LinkedList<Instance> create;
    static LinkedList<Instance> destroy;
    static {
        change = new LinkedList<EventQueue.ChangeInstance>();
        create = new LinkedList<Instance>();
        destroy = new LinkedList<Instance>();
    }

    public static void processChange(RoomInstance r) {
        if (change.isEmpty())
            return;
        for (ChangeInstance i : change) {
            // remove from here
            r.getObjectGroup(i.oldObjId).instances.remove(i.inst);
            // and add to here
            r.createObjectGroup(i.inst.obj.getId()).add(i.inst);
        }
        change.clear();
    }

    public static void processCreate(RoomInstance r) {
        if (create.isEmpty())
            return;
        for (Instance i : create)
            r.createObjectGroup(i.obj.getId()).instances.add(i);
        create.clear();
    }

    public static void processDestroy(RoomInstance r) {
        if (destroy.isEmpty())
            return;
        for (Instance i : destroy) {
            ObjectGroup g = r.getObjectGroup(i.obj.getId());
            if (g == null)
                return;
            g.instances.remove(i);
            if (g.instances.size() == 0) {
                r.instanceGroups.remove(g);
            }
        }
        destroy.clear();
    }

    private EventQueue()
        {
        }

    public static void addChangeEvent(Instance i, int oldId) {
        change.add(new ChangeInstance(i, oldId));
    }

    public static void addCreateEvent(Instance i) {
        create.add(i);
    }

    public static void addDestroyEvent(Instance i) {
        destroy.add(i);
    }

    private static class ChangeInstance {
        int oldObjId;
        Instance inst;

        public ChangeInstance(Instance i, int oldId)
            {
                this.inst = i;
            }
    }

}
