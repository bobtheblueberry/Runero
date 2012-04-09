package org.gcreator.runero.event;

import java.util.ArrayList;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameTimeline;

public class Event {

    // mouse event types
    public static final byte EV_LEFT_BUTTON = 0;
    public static final byte EV_RIGHT_BUTTON = 1;
    public static final byte EV_MIDDLE_BUTTON = 2;
    public static final byte EV_NO_BUTTON = 3;
    public static final byte EV_LEFT_PRESS = 4;
    public static final byte EV_RIGHT_PRESS = 5;
    public static final byte EV_MIDDLE_PRESS = 6;
    public static final byte EV_LEFT_RELEASE = 7;
    public static final byte EV_RIGHT_RELEASE = 8;
    public static final byte EV_MIDDLE_RELEASE = 9;
    public static final byte EV_MOUSE_ENTER = 10;
    public static final byte EV_MOUSE_LEAVE = 11;
    public static final byte EV_MOUSE_WHEEL_UP = 60;
    public static final byte EV_MOUSE_WHEEL_DOWN = 61;
    public static final byte EV_GLOBAL_LEFT_BUTTON = 50;
    public static final byte EV_GLOBAL_RIGHT_BUTTON = 51;
    public static final byte EV_GLOBAL_MIDDLE_BUTTON = 52;
    public static final byte EV_GLOBAL_LEFT_PRESS = 53;
    public static final byte EV_GLOBAL_RIGHT_PRESS = 54;
    public static final byte EV_GLOBAL_MIDDLE_PRESS = 55;
    public static final byte EV_GLOBAL_LEFT_RELEASE = 56;
    public static final byte EV_GLOBAL_RIGHT_RELEASE = 57;
    public static final byte EV_GLOBAL_MIDDLE_RELEASE = 58;
    public static final byte EV_JOYSTICK1_LEFT = 16;
    public static final byte EV_JOYSTICK1_RIGHT = 17;
    public static final byte EV_JOYSTICK1_UP = 18;
    public static final byte EV_JOYSTICK1_DOWN = 19;
    public static final byte EV_JOYSTICK1_BUTTON1 = 21;
    public static final byte EV_JOYSTICK1_BUTTON2 = 22;
    public static final byte EV_JOYSTICK1_BUTTON3 = 23;
    public static final byte EV_JOYSTICK1_BUTTON4 = 24;
    public static final byte EV_JOYSTICK1_BUTTON5 = 25;
    public static final byte EV_JOYSTICK1_BUTTON6 = 26;
    public static final byte EV_JOYSTICK1_BUTTON7 = 27;
    public static final byte EV_JOYSTICK1_BUTTON8 = 28;
    public static final byte EV_JOYSTICK2_LEFT = 31;
    public static final byte EV_JOYSTICK2_RIGHT = 32;
    public static final byte EV_JOYSTICK2_UP = 33;
    public static final byte EV_JOYSTICK2_DOWN = 34;
    public static final byte EV_JOYSTICK2_BUTTON1 = 36;
    public static final byte EV_JOYSTICK2_BUTTON2 = 37;
    public static final byte EV_JOYSTICK2_BUTTON3 = 38;
    public static final byte EV_JOYSTICK2_BUTTON4 = 39;
    public static final byte EV_JOYSTICK2_BUTTON5 = 40;
    public static final byte EV_JOYSTICK2_BUTTON6 = 41;
    public static final byte EV_JOYSTICK2_BUTTON7 = 42;
    public static final byte EV_JOYSTICK2_BUTTON8 = 43;

    // other event types
    public static final byte EV_OUTSIDE = 0;
    public static final byte EV_BOUNDARY = 1;
    public static final byte EV_GAME_START = 2;
    public static final byte EV_GAME_END = 3;
    public static final byte EV_ROOM_START = 4;
    public static final byte EV_ROOM_END = 5;
    public static final byte EV_NO_MORE_LIVES = 6;
    public static final byte EV_NO_MORE_HEALTH = 9;
    public static final byte EV_ANIMATION_END = 7;
    public static final byte EV_END_OF_PATH = 8;
    public static final byte EV_USER0 = 10;
    public static final byte EV_USER1 = 11;
    public static final byte EV_USER2 = 12;
    public static final byte EV_USER3 = 13;
    public static final byte EV_USER4 = 14;
    public static final byte EV_USER5 = 15;
    public static final byte EV_USER6 = 16;
    public static final byte EV_USER7 = 17;
    public static final byte EV_USER8 = 18;
    public static final byte EV_USER9 = 19;
    public static final byte EV_USER10 = 20;
    public static final byte EV_USER11 = 21;
    public static final byte EV_USER12 = 22;
    public static final byte EV_USER13 = 23;
    public static final byte EV_USER14 = 24;
    public static final byte EV_USER15 = 25;

    // step event types
    public static final byte EV_STEP_NORMAL = 0;
    public static final byte EV_STEP_BEGIN = 1;
    public static final byte EV_STEP_END = 2;

    // alarm event types
    public static final byte EV_ALARM0 = 0;
    public static final byte EV_ALARM1 = 1;
    public static final byte EV_ALARM2 = 2;
    public static final byte EV_ALARM3 = 3;
    public static final byte EV_ALARM4 = 4;
    public static final byte EV_ALARM5 = 5;
    public static final byte EV_ALARM6 = 6;
    public static final byte EV_ALARM7 = 7;
    public static final byte EV_ALARM8 = 8;
    public static final byte EV_ALARM9 = 9;
    public static final byte EV_ALARM10 = 10;
    public static final byte EV_ALARM11 = 11;

    public MainEvent parent;
    public byte type;
    public int collisionId; // for collision events
    public Instance collisionObject = null; // Object collided with in collision events
    public ArrayList<Action> actions;
    public GameObject object; // null for timeline events
    public GameTimeline timeline; // null for object events

    public Event(MainEvent parent, byte type) {
        this.parent = parent;
        this.type = type;
        actions = new ArrayList<Action>();
    }

    public void addAction(Action a) {
        actions.add(a);
    }
}
