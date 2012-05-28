package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class Repeat implements Statement {
    public ExprArgument condition;
    public ArrayList<Statement> exec;

    @Override
    public int execute(Context context) {
        int c = (int)condition.solve(context.instance, context.other).dVal;
        for (int i = 0; i < c; i++)
            context.execute(exec);
        return 0;
    }

}
