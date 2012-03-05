package org.gcreator.runero.gml;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameObject;

public class GmlInterpreter {

    public static void performAction(Action act, Instance instance, Event event) {

     /*   if (act.lib.registeredOnly) {
            System.out.println("Registered Only " + act.lib.description);
            javax.swing.JOptionPane.showMessageDialog(Runner.gl.getBaseGraphics().getComponent(), 
                    "ERROR! CANNOT CONTINUE! You must mail me Seven Dollars ($7) " +
                    "to use registered only game maker functions.", "Critical Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }*/
        
        if (!act.lib.canApplyTo || act.appliesTo == GameObject.OBJECT_SELF) {
            doAction(act, instance);
            return;
        }
        // Check to see who it applies to...
        if (act.appliesTo == GameObject.OBJECT_OTHER) {
            if (event.parent.mainEvent == MainEvent.EV_COLLISION) {
                doAction(act, event.collisionObject);
            } else {
                System.err.println("Error! reference to other outside collision event " + instance.getObject() + " Event " + event);
                return;
            }
        } else {
            // must apply to an object
            for (Instance i : RuneroGame.room.getInstances(act.appliesTo)) {
                doAction(act, i);
            }
        }
    }

    
    private static void doAction(Action act, Instance instance) {
        
        switch (act.lib.actionKind) {
        case Action.ACT_NORMAL:
            // Arguments
            // is it a question?
            // is it relative?
            // stuff
            // stuff
            //System.out.println("Normal action! " + act.lib.description);
            if (act.lib.id == 102) {
                System.out.println("action set speed and direction");
                double dir = GmlParser.getDouble(act.arguments.get(0).val);
                double speed = GmlParser.getDouble(act.arguments.get(1).val);
                if (act.relative) {
                    instance.motion_add(dir, speed);
                } else {
                    instance.motion_set(dir, speed);
                }
            }
            break;
        case Action.ACT_BEGIN:
            // {
            break;
        case Action.ACT_END:
            // }
            break;
        case Action.ACT_ELSE:
            // else
            break;
        case Action.ACT_EXIT:
            // exit
            break;
        case Action.ACT_REPEAT:
            // repeat (expression) 
            break;
        case Action.ACT_VARIABLE:
            // var = value
            // relative?
            break;
        case Action.ACT_CODE:
            // Execute code
            break;
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
    }
}
/* Old Action code, could be useful
case GameMakerLibrary.ACTION_MOVE:
           // Move Fixed..
           // Arrows.. yay
           Argument arg1 = arguments.get(0);
           double speed = GMLParser.getDouble(arguments.get(1).val);
           ArrayList<Integer> vals = new ArrayList<Integer>();
           for (int i = 0; i < 9; i++) {
               if (!arg1.val.substring(i).equals("1"))
                   continue;
               vals.add(i);
           }
           if (vals.size() == 0) {
               instance.setSpeed(speed);
               break;
           }
           int r = (int) (Math.random() * vals.size());
           int x = vals.get(r);
           if (x == 4) { // stop
               instance.setSpeed(0);
               break;
           }
           int d = 0;
           if (r == 0)
               d = 315;
           else if (r == 1)
               d = 270;
           else if (r == 2)
               d = 225;
           else if (r == 3)
               d = 0;
           else if (r == 5)
               d = 180;
           else if (r == 6)
               d = 45;
           else if (r == 7)
               d = 90;
           else if (r == 8)
               d = 135;
           if (relative)
               speed += instance.getSpeed();
           instance.motion_set(d, speed);
           break;
           */
