package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.gml.Constant;

public class If implements Statement {

    public ExprArgument condition;
    public ArrayList<Statement> exec;

    public boolean hasElse;
    public ArrayList<Statement> elseExec;

    @Override
    public int execute(Context context) {
        Constant c = condition.solve(context.instance, context.other);
        if (c.dVal > 0.5)
            context.execute(exec);
        else if (hasElse)
            context.execute(elseExec);
        return 0;
    }

}
