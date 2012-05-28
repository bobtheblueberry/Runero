package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class While implements Statement {

    public ExprArgument condition;
    public ArrayList<Statement> exec;
    
    @Override
    public int execute(Context context) {
        while (condition.solve(context.instance, context.other).dVal > 0.5)
            context.execute(exec);
        return 0;
    }

}
