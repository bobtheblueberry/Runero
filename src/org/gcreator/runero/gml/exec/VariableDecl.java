package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class VariableDecl implements Statement {

    public ArrayList<String> vars;
    public boolean global;

    public VariableDecl(boolean global) {
        this.global = global;
        vars = new ArrayList<String>();
    }

    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }

}
