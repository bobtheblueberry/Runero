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

    public MainEvent(byte n) {
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

}
