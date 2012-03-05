package org.gcreator.runero.event;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.*;
import org.lateralgm.resources.library.RLibAction;

public class Action {

    public static final int ACT_NORMAL = 0;
    public static final int ACT_BEGIN = 1;
    public static final int ACT_END = 2;
    public static final int ACT_ELSE = 3;
    public static final int ACT_EXIT = 4;
    public static final int ACT_REPEAT = 5;
    public static final int ACT_VARIABLE = 6;
    public static final int ACT_CODE = 7;
    public static final int ACT_PLACEHOLDER = 8;
    public static final int ACT_SEPARATOR = 9;
    public static final int ACT_LABEL = 10;

    public static final int EXEC_NONE = 0;
    public static final int EXEC_FUNCTION = 1;
    public static final int EXEC_CODE = 2;

    public boolean relative = false;
    public boolean not = false;
    public int appliesTo;

    public final RLibAction lib;
    public ArrayList<Argument> arguments;
    
    public Action(RLibAction lib) {
        this.lib = lib;
        arguments = new ArrayList<Argument>();
    }
    
    public void performAction(Instance instance, Event event) {

        if (lib.registeredOnly) {
            JOptionPane.showMessageDialog(Runner.gl.getBaseGraphics().getComponent(), 
                    "ERROR! CANNOT CONTINUE! You must mail me Seven Dollars ($7) " +
            		"to use registered only game maker functions.", "Critical Error",JOptionPane.ERROR_MESSAGE);
            RuneroGame.game.stop();
        }
        
        if (!lib.canApplyTo || appliesTo == GameObject.OBJECT_SELF) {
            doAction(instance);
            return;
        }
        // Check to see who it applies to...
        if (appliesTo == GameObject.OBJECT_OTHER) {
            if (event.parent.mainEvent == MainEvent.EV_COLLISION) {
                doAction(event.collisionObject);
            } else {
                System.err.println("Error! reference to other outside collision event " + instance.getObject() + " Event " + event);
                return;
            }
        } else {
            // must apply to an object
            for (Instance i : RuneroGame.room.getInstances(appliesTo)) {
                doAction(i);
            }
        }
    }

    
    private void doAction(Instance instance) {
        
        switch (lib.actionKind) {
        case Action.ACT_NORMAL:
            // Arguments
            // is it a question?
            // is it relative?
            // stuff
            // stuff
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
