package org.gcreator.runero.event;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.gml.lib.ActionLibrary;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameObject;

public class ActionInterpreter {

    public static void performEvent(Event e, Instance instance) {
        for (int i = 0; i < e.actions.size(); i++) {
            Action act = e.actions.get(i);
            try {
                i = performAction(act, e, instance, i);
            } catch (Exception exc) {
                System.out.println("Error performing action " + act.lib.name + ":" + act.lib.id + " for object "
                        + e.object.getName());
                exc.printStackTrace();
            }
            if (i < 0) // EXIT
                return;
        }
    }

    private static int executeBlock(Action.BlockAction ba, Instance instance, Event e, int i) {
        if (ba.isFake)
            return i;

        for (int x = ba.start; x <= ba.end; x++) {
            Action act = e.actions.get(x);
            x = performAction(act, e, instance, x);
            if (x < 0)
                return -1;// EXIT
        }

        return ba.actionEnd;
    }

    private static int performAction(Action act, Event e, Instance instance, int i) {
        switch (act.lib.actionKind) {
            case Action.ACT_NORMAL:
            case Action.ACT_VARIABLE:
            case Action.ACT_CODE:

                if (act.lib.actionKind == ActionLibrary.INHERITED) {
                    // TODO: inherited events automatically
                    if (instance.parentId >= 0) {
                        if (instance.parentId == instance.obj.getId()) {
                            System.out.println("cant be yo own daddy bro");
                            break;
                        }
                        GameObject parent = RuneroGame.game.getObject(instance.parentId);
                        if (parent == null) {
                            System.out.println("cannot event inherited; null parent");
                            break;
                        }
                        // look for parent event
                        if (!parent.hasEvent(e.parent.mainEvent)) {
                            break;
                        }
                        for (Event event : parent.getMainEvent(e.parent.mainEvent).events) {
                            if (event.type == e.type) {
                                event.collisionObject = e.collisionObject;
                                performEvent(event, instance);
                            }
                        }
                    }
                    break;
                }

                boolean result = performAction(act, instance, e);
                if (act.lib.question) {
                    if (result) {
                        return executeBlock(act.ifAction, instance, e, i);
                    } else if (act.elseAction != null) {
                        return executeBlock(act.elseAction, instance, e, i);
                    } else {
                        i = act.ifAction.actionEnd;
                    }
                }
                break;
            case Action.ACT_BEGIN:
                // { should not happen
                break;
            case Action.ACT_END:
                // } should not happen
                break;
            case Action.ACT_ELSE:
                // else the game programmer is an idiot
                break;
            case Action.ACT_EXIT:
                // stop performing event.
                return -1;
            case Action.ACT_REPEAT:
                // TODO: Check to see what types GM uses
                int times = Integer.parseInt(act.arguments.get(0).val);
                int last = i;
                for (int t = 0; t < times; t++)
                    last = executeBlock(act.repeatAction, instance, e, i);
                return last;
            case Action.ACT_PLACEHOLDER:
                // Useless
                break;
            case Action.ACT_SEPARATOR:
                // Useless
                break;
            case Action.ACT_LABEL:
                // Also useless
                break;
            default:
                System.err.println("This can't ever happen");
                break;
        }
        return i;
    }

    /**
     * returns true if it is not a question if it is a question it evaluates the
     * question and returns the result
     * 
     * @param act
     * @param instance
     * @param event
     * @return
     */
    private static boolean performAction(Action act, Instance instance, Event event) {
        /*   if (act.lib.registeredOnly) {
        System.out.println("Registered Only " + act.lib.description);
        javax.swing.JOptionPane.showMessageDialog(Runner.gl.getBaseGraphics().getComponent(), 
                "ERROR! CANNOT CONTINUE! You must send me Seven Dollars ($7) " +
                "to use registered only game maker functions.", "Critical Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        }*/

        if (!act.lib.canApplyTo || act.appliesTo == GameObject.OBJECT_SELF) {
            if (act.lib.question)
                return ActionLibrary.executeQuestion(act, instance, event.collisionObject);
            else
                ActionLibrary.executeAction(act, instance, event.collisionObject);
            return true;
        }
        // Check to see who it applies to...
        if (act.appliesTo == GameObject.OBJECT_OTHER) {
            if (event.parent.mainEvent == MainEvent.EV_COLLISION) {
                if (act.lib.question)
                    return ActionLibrary.executeQuestion(act, event.collisionObject, instance);
                else
                    ActionLibrary.executeAction(act, event.collisionObject, instance);
                return true;

            } else {
                System.err.println("Error! reference to other outside collision event " + instance.obj + " Event "
                        + event);
                return true;
            }
        } else {

            // must apply to an object
            if (RuneroGame.room.hasObjectGroup(act.appliesTo))
                for (Instance i : RuneroGame.room.getObjectGroup(act.appliesTo).instances) {
                    if (act.lib.question) {
                        return ActionLibrary.executeQuestion(act, i, event.collisionObject);
                    } else
                        ActionLibrary.executeAction(act, i, event.collisionObject);
                }
        }
        return true;
    }
}
