package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class For implements Statement {

    public Statement initial;
    public Argument condition;
    public Statement increcement;
    public ArrayList<Statement> code;
    
    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }

}
