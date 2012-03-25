package org.gcreator.runero.gml.exec;

import java.util.ArrayList;


public class Function implements Statement {
    public String name;
    public ArrayList<Argument> args;
    
    public Function(String name) {
        this.name = name;
        args = new ArrayList<Argument>();
    }

    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }
}
