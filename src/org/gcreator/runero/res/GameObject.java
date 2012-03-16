package org.gcreator.runero.res;

import java.util.ArrayList;

import org.gcreator.runero.event.MainEvent;

public class GameObject extends GameResource implements Comparable<GameObject> {

    public static final int OBJECT_SELF = -1;
    public static final int OBJECT_OTHER = -2;
    public static final int OBJECT_NONE = -100;

    public int spriteId;
    public boolean solid;
    public boolean visible;
    public int depth;
    public boolean persistent;
    public int parentId;
    public int maskId; // sprite

    private ArrayList<MainEvent> mainEvents;

    public GameObject(String name) {
        super(name);
        mainEvents = new ArrayList<MainEvent>();
    }

    public MainEvent getMainEvent(byte index) {
        for (MainEvent e : mainEvents) {
            if (e.mainEvent == index) {
                return e;
            }
        }
        MainEvent e = new MainEvent(index);
        mainEvents.add(e);
        return e;
    }

    public boolean hasEvent(int mainEvent) {
        for (MainEvent e : mainEvents) {
            if (e.mainEvent == mainEvent) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<MainEvent> getMainEvents() {
        return mainEvents;
    }

    @Override
    public int compareTo(GameObject o) {
        return Integer.compare(depth, o.depth);
    }
}
