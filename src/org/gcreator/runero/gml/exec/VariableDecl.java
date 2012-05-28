package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.gml.VariableVal;

public class VariableDecl implements Statement {

    public ArrayList<String> vars;
    public boolean global;

    public VariableDecl(boolean global) {
        this.global = global;
        vars = new ArrayList<String>();
    }

    @Override
    public int execute(Context context) {
        for (String s : vars) {
            context.scope.vars.put(s, VariableVal.ZERO); // This is not the best thing to do. should fix sometime (maybe)
        }
        return 0;
    }

}
