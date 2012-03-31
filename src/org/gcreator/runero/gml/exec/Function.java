package org.gcreator.runero.gml.exec;

import java.util.ArrayList;


public class Function implements Statement {
    public String name;
    public ArrayList<ExprArgument> args;
    
    public Function(String name) {
        this.name = name;
        args = new ArrayList<ExprArgument>();
    }

    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }
}
