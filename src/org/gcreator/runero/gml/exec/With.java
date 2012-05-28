package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.res.GameObject;

public class With implements Statement {

    public ExprArgument who;
    public ArrayList<Statement> code;

    @Override
    public int execute(Context context) {
        int ref = (int) who.solve(context.instance, context.other).dVal;
        Instance with = null;
        if (ref == -3) // all
            ;
        else if (ref == -1) {// self
            with = context.instance;
        } else if (ref == -2) { // other
            with = context.other;
            if (with == null) {
                Runner.Error("With statement; other object is no set");
                return 0;
            }
        } else if (ref > 100000) {
            with = RuneroGame.room.getInstance(ref);
            if (with == null) {
                Runner.Error("With statement; Unknown instance with ID " + ref);
                return 0;
            }
        } else {
            // look for object
            GameObject g = RuneroGame.game.getObject(ref);
            if (g == null) {
                Runner.Error("With statement; Unknown object ID " + ref);
                return 0;
            }
            ObjectGroup o = RuneroGame.room.getObjectGroup(ref);
            if (o == null || o.instances.isEmpty()) {
                Runner.Error("With statement; No instance of " + g.getName() + " in room");
                return 0;
            }
            with = o.instances.get(0);
        }
        if (with == null) {
            Runner.Error("With Statement; Cannot solve object number " + ref);
            return 0;
        }

        Instance inst = context.instance;
        Instance other = context.other;

        context.other = inst;
        if (ref == -3) { // all
            for (ObjectGroup g : RuneroGame.room.instanceGroups)
                for (Instance i : g.instances) {
                    context.instance = i;
                    context.execute(code);
                }
        } else {
            context.instance = with;
            context.execute(code);
        }
        context.instance = inst;
        context.other = other;

        return 0;
    }
}
