package org.gcreator.runero.event;

import java.util.ArrayList;

public class MainEvent implements Comparable<MainEvent> {

    /* Main Events */

    public static final byte EV_CREATE = 0;
    public static final byte EV_DESTROY = 1;
    public static final byte EV_ALARM = 2;
    public static final byte EV_STEP = 3;
    public static final byte EV_COLLISION = 4;
    public static final byte EV_KEYBOARD = 5;
    public static final byte EV_MOUSE = 6;
    public static final byte EV_OTHER = 7;
    public static final byte EV_DRAW = 8;
    public static final byte EV_KEYPRESS = 9;
    public static final byte EV_KEYRELEASE = 10;
    public static final byte EV_TRIGGER = 11;

    public byte mainEvent;
    public ArrayList<Event> events;

    public MainEvent(byte n)
        {
            mainEvent = n;
            events = new ArrayList<Event>();
        }

    public void addEvent(Event e) {
        events.add(e);
    }

    @Override
    public int compareTo(MainEvent o) {
        return Byte.valueOf(mainEvent).compareTo(o.mainEvent);
    }

    public String toString() {
        return getEventName(mainEvent) + " Event";
    }

    public String getEventName(int i) {
        if (i == 0)
            return "Create";
        if (i == 1)
            return "Destroy";
        if (i == 2)
            return "Alarm";
        if (i == 3)
            return "Step";
        if (i == 4)
            return "Collision";
        if (i == 5)
            return "Keyboard";
        if (i == 6)
            return "Mouse";
        if (i == 7)
            return "Other";
        if (i ==8 )
            return "Draw";
        if (i == 9)
            return "Key Press";
        if (i == 10)
            return "Key Release";
        if (i == 11)
            return "Trigger Event";
        return "<unknown>";
    }
}
