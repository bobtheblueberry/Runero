package org.gcreator.runero.event;

import java.util.LinkedList;

import org.gcreator.runero.res.GameObject;

/**
 * EventManger is a nifty class that manages the game so it only checks and
 * calls events when an object has the event in its code
 * 
 * @author serge
 * 
 */
public class EventManager {

    public void addObject(GameObject obj) {
        for (MainEvent me : obj.getMainEvents()) {
            for (Event e : me.events) {
                switch (me.mainEvent) {
                    case MainEvent.EV_CREATE:
                        hasCreateEvents = true;
                        if (create == null)
                            create = new LinkedList<Event>();
                        create.add(e);
                        break;
                    case MainEvent.EV_DESTROY:
                        hasDestroyEvents = true;
                        if (destroy == null)
                            destroy = new LinkedList<Event>();
                        destroy.add(e);
                        break;
                    case MainEvent.EV_DRAW:
                        // Draw events are handled differently
                        break;
                    case MainEvent.EV_COLLISION:
                        hasCollisionEvents = true;
                        if (collision == null)
                            collision = new LinkedList<CollisionEvent>();
                        collision.add(new CollisionEvent(e, e.collisionId));
                        break;
                    case MainEvent.EV_STEP:
                        addStepEvent(e);
                        break;
                    case MainEvent.EV_TRIGGER:
                        System.err.println("Unsupported trigger event in object " + obj.getName());
                    case MainEvent.EV_ALARM:
                        hasAlarmEvents = true;
                        addAlarmEvent(e);
                        break;
                    case MainEvent.EV_KEYBOARD:
                        hasKeyboardEvents = true;
                        if (e.type == 0) {
                            if (keyboardNoEvents == null)
                                keyboardNoEvents = new LinkedList<Event>();
                            keyboardNoEvents.add(e);
                        } else if (e.type == 1) {
                            if (keyboardAnyEvents == null)
                                keyboardAnyEvents = new LinkedList<Event>();
                            keyboardAnyEvents.add(e);
                        } else {
                            if (keyboardEvents == null)
                                keyboardEvents = new LinkedList<Event>();
                            keyboardEvents.add(e);
                        }
                        break;
                    case MainEvent.EV_KEYPRESS:
                        hasKeyPressEvents = true;
                        if (e.type == 0) {
                            if (keyPressNoEvents == null)
                                keyPressNoEvents = new LinkedList<Event>();
                            keyPressNoEvents.add(e);
                        } else if (e.type == 1) {
                            if (keyPressAnyEvents == null)
                                keyPressAnyEvents = new LinkedList<Event>();
                            keyPressAnyEvents.add(e);
                        } else {
                            if (keyPressEvents == null)
                                keyPressEvents = new LinkedList<Event>();
                            keyPressEvents.add(e);
                        }
                        break;
                    case MainEvent.EV_KEYRELEASE:
                        hasKeyReleaseEvents = true;
                        if (e.type == 0) {
                            if (keyReleaseNoEvents == null)
                                keyReleaseNoEvents = new LinkedList<Event>();
                            keyReleaseNoEvents.add(e);
                        } else if (e.type == 1) {
                            if (keyReleaseAnyEvents == null)
                                keyReleaseAnyEvents = new LinkedList<Event>();
                            keyReleaseAnyEvents.add(e);
                        } else {
                            if (keyReleaseEvents == null)
                                keyReleaseEvents = new LinkedList<Event>();
                            keyReleaseEvents.add(e);
                        }
                        break;
                    case MainEvent.EV_MOUSE:
                        hasMouseEvents = true;
                        addMouseEvent(e);
                        break;
                    case MainEvent.EV_OTHER:
                        hasOtherEvents = true;
                        addOtherEvent(e);
                        break;
                }
            }
        }
    }

    private void addOtherEvent(Event e) {
        if (e.type > 9)
            hasUserDefinedEvents = true;
        switch (e.type) {
            case Event.EV_OUTSIDE:
                if (otherOutsideRoom == null)
                    otherOutsideRoom = new LinkedList<Event>();
                otherOutsideRoom.add(e);
                break;
            case Event.EV_BOUNDARY:
                if (otherBoundary == null)
                    otherBoundary = new LinkedList<Event>();
                otherBoundary.add(e);
                break;
            case Event.EV_GAME_START:
                if (otherGameStart == null)
                    otherGameStart = new LinkedList<Event>();
                otherGameStart.add(e);
                break;
            case Event.EV_GAME_END:
                if (otherGameEnd == null)
                    otherGameEnd = new LinkedList<Event>();
                otherGameEnd.add(e);
                break;
            case Event.EV_NO_MORE_LIVES:
                if (otherNoMoreLives == null)
                    otherNoMoreLives = new LinkedList<Event>();
                otherNoMoreLives.add(e);
                break;
            case Event.EV_ANIMATION_END:
                if (otherAnimationEnd == null)
                    otherAnimationEnd = new LinkedList<Event>();
                otherAnimationEnd.add(e);
                break;
            case Event.EV_END_OF_PATH:
                if (otherEndofPath == null)
                    otherEndofPath = new LinkedList<Event>();
                otherEndofPath.add(e);
                break;
            case Event.EV_NO_MORE_HEALTH:
                if (otherNoMoreHealth == null)
                    otherNoMoreHealth = new LinkedList<Event>();
                otherNoMoreHealth.add(e);
                break;
            case Event.EV_USER0:
                if (otherUserDefined0 == null)
                    otherUserDefined0 = new LinkedList<Event>();
                otherUserDefined0.add(e);
                break;
            case Event.EV_USER1:
                if (otherUserDefined1 == null)
                    otherUserDefined1 = new LinkedList<Event>();
                otherUserDefined1.add(e);
                break;
            case Event.EV_USER2:
                if (otherUserDefined2 == null)
                    otherUserDefined2 = new LinkedList<Event>();
                otherUserDefined2.add(e);
                break;
            case Event.EV_USER3:
                if (otherUserDefined3 == null)
                    otherUserDefined3 = new LinkedList<Event>();
                otherUserDefined3.add(e);
                break;
            case Event.EV_USER4:
                if (otherUserDefined4 == null)
                    otherUserDefined4 = new LinkedList<Event>();
                otherUserDefined4.add(e);
                break;
            case Event.EV_USER5:
                if (otherUserDefined5 == null)
                    otherUserDefined5 = new LinkedList<Event>();
                otherUserDefined5.add(e);
                break;
            case Event.EV_USER6:
                if (otherUserDefined6 == null)
                    otherUserDefined6 = new LinkedList<Event>();
                otherUserDefined6.add(e);
                break;
            case Event.EV_USER7:
                if (otherUserDefined7 == null)
                    otherUserDefined7 = new LinkedList<Event>();
                otherUserDefined7.add(e);
                break;
            case Event.EV_USER8:
                if (otherUserDefined8 == null)
                    otherUserDefined8 = new LinkedList<Event>();
                otherUserDefined8.add(e);
                break;
            case Event.EV_USER9:
                if (otherUserDefined9 == null)
                    otherUserDefined9 = new LinkedList<Event>();
                otherUserDefined9.add(e);
                break;
            case Event.EV_USER10:
                if (otherUserDefined10 == null)
                    otherUserDefined10 = new LinkedList<Event>();
                otherUserDefined10.add(e);
                break;
            case Event.EV_USER11:
                if (otherUserDefined11 == null)
                    otherUserDefined11 = new LinkedList<Event>();
                otherUserDefined11.add(e);
                break;
            case Event.EV_USER12:
                if (otherUserDefined12 == null)
                    otherUserDefined12 = new LinkedList<Event>();
                otherUserDefined12.add(e);
                break;
            case Event.EV_USER13:
                if (otherUserDefined13 == null)
                    otherUserDefined13 = new LinkedList<Event>();
                otherUserDefined13.add(e);
                break;
            case Event.EV_USER14:
                if (otherUserDefined14 == null)
                    otherUserDefined14 = new LinkedList<Event>();
                otherUserDefined14.add(e);
                break;
            case Event.EV_USER15:
                if (otherUserDefined15 == null)
                    otherUserDefined15 = new LinkedList<Event>();
                otherUserDefined15.add(e);
                break;

        }
    }

    private void addMouseEvent(Event e) {
        if (e.type >= 50)
            hasGlobalMouseEvents = true;
        else if (e.type > 15 && e.type <= 28)
            hasJoyStick1Events = true;
        else if (e.type >= 31 && e.type <= 43)
            hasJoystick2Events = true;

        switch (e.type) {
            case Event.EV_LEFT_BUTTON:
                if (mouseLeftButton == null)
                    mouseLeftButton = new LinkedList<Event>();
                mouseLeftButton.add(e);
                break;
            case Event.EV_RIGHT_BUTTON:
                if (mouseRightButton == null)
                    mouseRightButton = new LinkedList<Event>();
                mouseRightButton.add(e);
                break;
            case Event.EV_MIDDLE_BUTTON:
                if (mouseMiddleButton == null)
                    mouseMiddleButton = new LinkedList<Event>();
                mouseMiddleButton.add(e);
                break;
            case Event.EV_LEFT_PRESS:
                if (mouseLeftPressed == null)
                    mouseLeftPressed = new LinkedList<Event>();
                mouseLeftPressed.add(e);
                break;
            case Event.EV_RIGHT_PRESS:
                if (mouseRightPressed == null)
                    mouseRightPressed = new LinkedList<Event>();
                mouseRightPressed.add(e);
                break;
            case Event.EV_MIDDLE_PRESS:
                if (mouseMiddlePressed == null)
                    mouseMiddlePressed = new LinkedList<Event>();
                mouseMiddlePressed.add(e);
                break;
            case Event.EV_LEFT_RELEASE:
                if (mouseLeftReleased == null)
                    mouseLeftReleased = new LinkedList<Event>();
                mouseLeftReleased.add(e);
                break;
            case Event.EV_RIGHT_RELEASE:
                if (mouseRightReleased == null)
                    mouseRightReleased = new LinkedList<Event>();
                mouseRightReleased.add(e);
                break;
            case Event.EV_MIDDLE_RELEASE:
                if (mouseMiddleReleased == null)
                    mouseMiddleReleased = new LinkedList<Event>();
                mouseMiddleReleased.add(e);
                break;
            case Event.EV_NO_BUTTON:
                hasMouseAreaEvents = true;
                if (mouseNoButton == null)
                    mouseNoButton = new LinkedList<Event>();
                mouseNoButton.add(e);
                break;
            case Event.EV_MOUSE_ENTER:
                hasMouseAreaEvents = true;
                if (mouseEnter == null)
                    mouseEnter = new LinkedList<Event>();
                mouseEnter.add(e);
                break;
            case Event.EV_MOUSE_LEAVE:
                hasMouseAreaEvents = true;
                if (mouseLeave == null)
                    mouseLeave = new LinkedList<Event>();
                mouseLeave.add(e);
                break;
            case Event.EV_MOUSE_WHEEL_UP:
                hasMouseWheelEvents = true;
                if (mouseWheelUp == null)
                    mouseWheelUp = new LinkedList<Event>();
                mouseWheelUp.add(e);
                break;
            case Event.EV_MOUSE_WHEEL_DOWN:
                hasMouseWheelEvents = true;
                if (mouseWheelDown == null)
                    mouseWheelDown = new LinkedList<Event>();
                mouseWheelDown.add(e);
                break;
            case Event.EV_GLOBAL_LEFT_BUTTON:
                if (mouseGlobalLeftButton == null)
                    mouseGlobalLeftButton = new LinkedList<Event>();
                mouseGlobalLeftButton.add(e);
                break;
            case Event.EV_GLOBAL_RIGHT_BUTTON:
                if (mouseGlobalRightButton == null)
                    mouseGlobalRightButton = new LinkedList<Event>();
                mouseGlobalRightButton.add(e);
                break;
            case Event.EV_GLOBAL_MIDDLE_BUTTON:
                if (mouseGlobalMiddleButton == null)
                    mouseGlobalMiddleButton = new LinkedList<Event>();
                mouseGlobalMiddleButton.add(e);
                break;
            case Event.EV_GLOBAL_LEFT_PRESS:
                if (mouseGlobalLeftPressed == null)
                    mouseGlobalLeftPressed = new LinkedList<Event>();
                mouseGlobalLeftPressed.add(e);
                break;
            case Event.EV_GLOBAL_RIGHT_PRESS:
                if (mouseGlobalRightPressed == null)
                    mouseGlobalRightPressed = new LinkedList<Event>();
                mouseGlobalRightPressed.add(e);
                break;
            case Event.EV_GLOBAL_MIDDLE_PRESS:
                if (mouseGlobalMiddlePressed == null)
                    mouseGlobalMiddlePressed = new LinkedList<Event>();
                mouseGlobalMiddlePressed.add(e);
                break;
            case Event.EV_GLOBAL_LEFT_RELEASE:
                if (mouseGlobalLeftReleased == null)
                    mouseGlobalLeftReleased = new LinkedList<Event>();
                mouseGlobalLeftReleased.add(e);
                break;
            case Event.EV_GLOBAL_RIGHT_RELEASE:
                if (mouseGlobalRightReleased == null)
                    mouseGlobalRightReleased = new LinkedList<Event>();
                mouseGlobalRightReleased.add(e);
                break;
            case Event.EV_GLOBAL_MIDDLE_RELEASE:
                if (mouseGlobalMiddleReleased == null)
                    mouseGlobalMiddleReleased = new LinkedList<Event>();
                mouseGlobalMiddleReleased.add(e);
                break;
            // joystick....
            case Event.EV_JOYSTICK1_LEFT:
                if (joystick1Left == null)
                    joystick1Left = new LinkedList<Event>();
                joystick1Left.add(e);
                break;
            case Event.EV_JOYSTICK1_RIGHT:
                if (joystick1Right == null)
                    joystick1Right = new LinkedList<Event>();
                joystick1Right.add(e);
                break;
            case Event.EV_JOYSTICK1_UP:
                if (joystick1Up == null)
                    joystick1Up = new LinkedList<Event>();
                joystick1Up.add(e);
                break;
            case Event.EV_JOYSTICK1_DOWN:
                if (joystick1Down == null)
                    joystick1Down = new LinkedList<Event>();
                joystick1Down.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON1:
                if (joystick1Button1 == null)
                    joystick1Button1 = new LinkedList<Event>();
                joystick1Button1.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON2:
                if (joystick1Button2 == null)
                    joystick1Button2 = new LinkedList<Event>();
                joystick1Button2.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON3:
                if (joystick1Button3 == null)
                    joystick1Button3 = new LinkedList<Event>();
                joystick1Button3.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON4:
                if (joystick1Button4 == null)
                    joystick1Button4 = new LinkedList<Event>();
                joystick1Button4.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON5:
                if (joystick1Button5 == null)
                    joystick1Button5 = new LinkedList<Event>();
                joystick1Button5.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON6:
                if (joystick1Button6 == null)
                    joystick1Button6 = new LinkedList<Event>();
                joystick1Button6.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON7:
                if (joystick1Button7 == null)
                    joystick1Button7 = new LinkedList<Event>();
                joystick1Button7.add(e);
                break;
            case Event.EV_JOYSTICK1_BUTTON8:
                if (joystick1Button8 == null)
                    joystick1Button8 = new LinkedList<Event>();
                joystick1Button8.add(e);
                break;
            case Event.EV_JOYSTICK2_LEFT:
                if (joystick2Left == null)
                    joystick2Left = new LinkedList<Event>();
                joystick2Left.add(e);
                break;
            case Event.EV_JOYSTICK2_RIGHT:
                if (joystick2Right == null)
                    joystick2Right = new LinkedList<Event>();
                joystick2Right.add(e);
                break;
            case Event.EV_JOYSTICK2_UP:
                if (joystick2Up == null)
                    joystick2Up = new LinkedList<Event>();
                joystick2Up.add(e);
                break;
            case Event.EV_JOYSTICK2_DOWN:
                if (joystick2Down == null)
                    joystick2Down = new LinkedList<Event>();
                joystick2Down.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON1:
                if (joystick2Button1 == null)
                    joystick2Button1 = new LinkedList<Event>();
                joystick2Button1.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON2:
                if (joystick2Button2 == null)
                    joystick2Button2 = new LinkedList<Event>();
                joystick2Button2.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON3:
                if (joystick2Button3 == null)
                    joystick2Button3 = new LinkedList<Event>();
                joystick2Button3.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON4:
                if (joystick2Button4 == null)
                    joystick2Button4 = new LinkedList<Event>();
                joystick2Button4.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON5:
                if (joystick2Button5 == null)
                    joystick2Button5 = new LinkedList<Event>();
                joystick2Button5.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON6:
                if (joystick2Button6 == null)
                    joystick2Button6 = new LinkedList<Event>();
                joystick2Button6.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON7:
                if (joystick2Button7 == null)
                    joystick2Button7 = new LinkedList<Event>();
                joystick2Button7.add(e);
                break;
            case Event.EV_JOYSTICK2_BUTTON8:
                if (joystick2Button8 == null)
                    joystick2Button8 = new LinkedList<Event>();
                joystick2Button8.add(e);
                break;
        }
    }

    private void addAlarmEvent(Event e) {
        switch (e.type) {
            case Event.EV_ALARM0:
                if (alarms[0] == null)
                    alarms[0] = new LinkedList<Event>();
                alarms[0].add(e);
                break;
            case Event.EV_ALARM1:
                if (alarms[1] == null)
                    alarms[1] = new LinkedList<Event>();
                alarms[1].add(e);
                break;
            case Event.EV_ALARM2:
                if (alarms[2] == null)
                    alarms[2] = new LinkedList<Event>();
                alarms[2].add(e);
                break;
            case Event.EV_ALARM3:
                if (alarms[3] == null)
                    alarms[3] = new LinkedList<Event>();
                alarms[3].add(e);
                break;
            case Event.EV_ALARM4:
                if (alarms[4] == null)
                    alarms[4] = new LinkedList<Event>();
                alarms[4].add(e);
                break;
            case Event.EV_ALARM5:
                if (alarms[5] == null)
                    alarms[5] = new LinkedList<Event>();
                alarms[5].add(e);
                break;
            case Event.EV_ALARM6:
                if (alarms[6] == null)
                    alarms[6] = new LinkedList<Event>();
                alarms[6].add(e);
                break;
            case Event.EV_ALARM7:
                if (alarms[7] == null)
                    alarms[7] = new LinkedList<Event>();
                alarms[7].add(e);
                break;
            case Event.EV_ALARM8:
                if (alarms[8] == null)
                    alarms[8] = new LinkedList<Event>();
                alarms[8].add(e);
                break;
            case Event.EV_ALARM9:
                if (alarms[9] == null)
                    alarms[9] = new LinkedList<Event>();
                alarms[9].add(e);
                break;
            case Event.EV_ALARM10:
                if (alarms[10] == null)
                    alarms[10] = new LinkedList<Event>();
                alarms[10].add(e);
                break;
            case Event.EV_ALARM11:
                if (alarms[11] == null)
                    alarms[11] = new LinkedList<Event>();
                alarms[11].add(e);
                break;
        }
    }

    private void addStepEvent(Event e) {
        switch (e.type) {
            case Event.EV_STEP_BEGIN:
                hasStepBeginEvents = true;
                if (stepBegin == null)
                    stepBegin = new LinkedList<Event>();
                stepBegin.add(e);
                break;
            case Event.EV_STEP_NORMAL:
                hasStepNormalEvents = true;
                if (stepNormal == null)
                    stepNormal = new LinkedList<Event>();
                stepNormal.add(e);
                break;
            case Event.EV_STEP_END:
                hasStepEndEvents = true;
                if (stepEnd == null)
                    stepEnd = new LinkedList<Event>();
                stepEnd.add(e);
                break;
        }
    }

    public boolean hasCreateEvents;
    public LinkedList<Event> create;
    public boolean hasDestroyEvents;
    public LinkedList<Event> destroy;
    // Draw events are handled differently;
    // alarm
    public boolean hasAlarmEvents;
    @SuppressWarnings("unchecked")
    public LinkedList<Event>[] alarms = new LinkedList[12];
    // step
    public boolean hasStepNormalEvents;
    public LinkedList<Event> stepNormal; // step middle
    public boolean hasStepBeginEvents;
    public LinkedList<Event> stepBegin;
    public boolean hasStepEndEvents;
    public LinkedList<Event> stepEnd;

    // collision - can't be dealt with as easily
    public boolean hasCollisionEvents;
    public LinkedList<CollisionEvent> collision;

    // keyboard
    public boolean hasKeyboardEvents;
    public LinkedList<Event> keyboardEvents;
    public LinkedList<Event> keyboardAnyEvents;
    public LinkedList<Event> keyboardNoEvents;
    // key press
    public boolean hasKeyPressEvents;
    public LinkedList<Event> keyPressEvents;
    public LinkedList<Event> keyPressAnyEvents;
    public LinkedList<Event> keyPressNoEvents;
    // key release
    public boolean hasKeyReleaseEvents;
    public LinkedList<Event> keyReleaseEvents;
    public LinkedList<Event> keyReleaseAnyEvents;
    public LinkedList<Event> keyReleaseNoEvents;
    // mouse
    public boolean hasMouseEvents;
    public LinkedList<Event> mouseLeftButton;
    public LinkedList<Event> mouseRightButton;
    public LinkedList<Event> mouseMiddleButton;
    public LinkedList<Event> mouseLeftPressed;
    public LinkedList<Event> mouseRightPressed;
    public LinkedList<Event> mouseMiddlePressed;
    public LinkedList<Event> mouseLeftReleased;
    public LinkedList<Event> mouseRightReleased;
    public LinkedList<Event> mouseMiddleReleased;
    public boolean hasMouseAreaEvents;
    public LinkedList<Event> mouseEnter;
    public LinkedList<Event> mouseLeave;
    public LinkedList<Event> mouseNoButton; // Stupid!
    public boolean hasMouseWheelEvents;
    public LinkedList<Event> mouseWheelUp;
    public LinkedList<Event> mouseWheelDown;
    // - global mouse
    public boolean hasGlobalMouseEvents;
    public LinkedList<Event> mouseGlobalLeftButton;
    public LinkedList<Event> mouseGlobalRightButton;
    public LinkedList<Event> mouseGlobalMiddleButton;
    public LinkedList<Event> mouseGlobalLeftPressed;
    public LinkedList<Event> mouseGlobalRightPressed;
    public LinkedList<Event> mouseGlobalMiddlePressed;
    public LinkedList<Event> mouseGlobalLeftReleased;
    public LinkedList<Event> mouseGlobalRightReleased;
    public LinkedList<Event> mouseGlobalMiddleReleased;
    // - joystick
    // NO ONE USES JOYSTICKS ANYMORE
    // - 1
    public boolean hasJoyStick1Events;
    public LinkedList<Event> joystick1Left;
    public LinkedList<Event> joystick1Right;
    public LinkedList<Event> joystick1Up;
    public LinkedList<Event> joystick1Down;
    public LinkedList<Event> joystick1Button1;
    public LinkedList<Event> joystick1Button2;
    public LinkedList<Event> joystick1Button3;
    public LinkedList<Event> joystick1Button4;
    public LinkedList<Event> joystick1Button5;
    public LinkedList<Event> joystick1Button6;
    public LinkedList<Event> joystick1Button7;
    public LinkedList<Event> joystick1Button8;
    // - 2
    public boolean hasJoystick2Events;
    public LinkedList<Event> joystick2Left;
    public LinkedList<Event> joystick2Right;
    public LinkedList<Event> joystick2Up;
    public LinkedList<Event> joystick2Down;
    public LinkedList<Event> joystick2Button1;
    public LinkedList<Event> joystick2Button2;
    public LinkedList<Event> joystick2Button3;
    public LinkedList<Event> joystick2Button4;
    public LinkedList<Event> joystick2Button5;
    public LinkedList<Event> joystick2Button6;
    public LinkedList<Event> joystick2Button7;
    public LinkedList<Event> joystick2Button8;

    // other
    public boolean hasOtherEvents;
    public LinkedList<Event> otherOutsideRoom;
    public LinkedList<Event> otherBoundary;
    public LinkedList<Event> otherGameStart;
    public LinkedList<Event> otherGameEnd;
    public LinkedList<Event> otherNoMoreLives;
    public LinkedList<Event> otherAnimationEnd;
    public LinkedList<Event> otherEndofPath;
    public LinkedList<Event> otherNoMoreHealth;
    // - user defined
    public boolean hasUserDefinedEvents;
    public LinkedList<Event> otherUserDefined0;
    public LinkedList<Event> otherUserDefined1;
    public LinkedList<Event> otherUserDefined2;
    public LinkedList<Event> otherUserDefined3;
    public LinkedList<Event> otherUserDefined4;
    public LinkedList<Event> otherUserDefined5;
    public LinkedList<Event> otherUserDefined6;
    public LinkedList<Event> otherUserDefined7;
    public LinkedList<Event> otherUserDefined8;
    public LinkedList<Event> otherUserDefined9;
    public LinkedList<Event> otherUserDefined10;
    public LinkedList<Event> otherUserDefined11;
    public LinkedList<Event> otherUserDefined12;
    public LinkedList<Event> otherUserDefined13;
    public LinkedList<Event> otherUserDefined14;
    public LinkedList<Event> otherUserDefined15;
}
