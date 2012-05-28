package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class For implements Statement {

    public Statement initial;
    public ExprArgument condition;
    public Statement increcement;
    public ArrayList<Statement> code;
    
    @Override
    public int execute(Context context) {
        initial.execute(context);
        while (condition.solve(context.instance, context.other).dVal > 0.5) {
            context.execute(code);
            increcement.execute(context);
        }
        return 0;
    }

}
