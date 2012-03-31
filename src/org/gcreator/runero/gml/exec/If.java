package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class If implements Statement {

    public ExprArgument condition;
    public ArrayList<Statement> exec;
    
    public boolean hasElse;
    public ArrayList<Statement> elseExec;
    
    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub
        
    }

}
