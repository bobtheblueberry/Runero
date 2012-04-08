package org.gcreator.runero.gml;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.gml.exec.Variable;
import org.gcreator.runero.gml.exec.VariableRef;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.res.GameObject;

public class VariableManager {

    private VariableManager()
        {
        }

    public static void setVariable(VariableRef r, Constant val, boolean relative, Instance instance, Instance other) {
        if (!relative) {
            setVariable(r, val, instance, other);
            return;
        }
        VariableVal v = findVariable(r, instance, other);
        if (v == null) {
            Runner.Error("no such variable " + r);
        }
        if (v.isString) {
            if (val.type == Constant.STRING)
                v.val += val.sVal;
            else
                v.val += val.dVal; // GM would bitch, but oh well
        } else {
            if (val.type == Constant.NUMBER)
                v.realVal += val.dVal;
            else
                Runner.Error("Error! cannot set number variable relative to a string!");
        }
    }

    public static void setVariable(VariableRef r, Constant val, Instance instance, Instance other) {
        Variable v = r.ref.get(0);
        if (r.ref.size() == 1) {
            String name = v.name;

            if (RuneroGame.game.constants.get(name) != null) {
                Runner.Error("Cannot set constant " + name);
                return;
            }

            if (v.isArray)
                name = GmlParser.getArrayName(v, instance, other);
            else if (v.isExpression) {
                Runner.Error("What r u doin???");
                return;
            }

            // look for the variable in global vars
            VariableVal var = RuneroGame.game.globalVars.get(name);
            if (var != null) {
                var.set(val);
                return;
            }
            // try to set it as a game var
            if (RuneroGame.game.setVariable(v, val))
                return;
            // guess we'll set it for the instance..
            instance.setVar(v, val, other);
        } else {
            System.out.println("TODO: Set variable ");
            // TODO: THIS
        }

    }

    public static VariableVal findVariable(VariableRef r, Instance instance, Instance other) {
        Variable v = r.ref.get(0);
        VariableVal val = getVariable(v, instance, other);
        
        if (r.ref.size() == 1) {
            if (v.isExpression) {
                Runner.Error("Invalid variable reference");
                return null;
            }
            return val;
        }
        // else
        int prev = (int) val.realVal;
        VariableVal value = null;
        for (int i = 1; i < r.ref.size(); i++) {
            value = getVar(prev, r.ref.get(i), instance, other);
            prev = (int) value.realVal;
        }

        return value;
    }

    private static VariableVal getVar(int ref, Variable v, Instance instance, Instance other) {
        String name = v.name;
        if (v.isArray)
            name = GmlParser.getArrayName(v, instance, other);
        else if (v.isExpression) {
            return new VariableVal(v.expression.solve(instance, other));
        }

        if (ref == -1) {// self
            return instance.getVariable(v, other);
        } else if (ref == -2) { // other
            return other.getVariable(v, other);
        } else if (ref == -3) { // all
            // shit
            System.out.println("'all' has no referential index");
            return null;
        } else if (ref == -4) {// noone
            // shit
            System.err.println("whos noone??");
            return null;
        } else if (ref == -5) {// global
            return RuneroGame.game.globalDotVars.get(name);
        } else if (ref == -7) {// local
            // ???
            return instance.getVariable(v, other);
        } else if (ref > 100000) {
            Instance i = RuneroGame.room.getInstance(ref);
            if (i == null) {
                Runner.Error("Unknown instance with ID " + ref);
                return null;
            }
            return i.getVariable(v, other);
        } else {
            // look for object
            GameObject g = RuneroGame.game.getObject(ref);
            if (g == null) {
                Runner.Error("Unknown object ID " + ref);
                return null;
            }
            ObjectGroup o = RuneroGame.room.getObjectGroup2(ref);
            if (o == null || o.instances.isEmpty()) {
                Runner.Error("No instance of " + g.getName() + " in room");
                return null;
            }
            return o.instances.get(0).getVariable(v, other);
        }
    }

    private static VariableVal getVariable(Variable v, Instance instance, Instance other) {
        VariableVal val;
        String name = v.name;
        if (v.isArray)
            name = GmlParser.getArrayName(v, instance, other);
        else if (v.isExpression) {
            return new VariableVal(v.expression.solve(instance, other));
        }
        // Look in built-in variables

        val = RuneroGame.game.getVariable(v, instance, other);
        if (val != null)
            return val;

        // Look in constants
        val = RuneroGame.game.constants.get(name);
        if (val != null)
            return val;
        // Look in global vars
        val = RuneroGame.game.globalVars.get(name);
        if (val != null)
            return val;
        // Look locally
        if (instance != null) {
            return instance.getVariable(v, other);
        }

        return null;
    }
}
