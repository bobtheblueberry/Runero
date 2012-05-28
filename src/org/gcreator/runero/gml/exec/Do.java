package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.Runner;
import org.gcreator.runero.gml.Constant;

public class Do implements Statement {

    public ArrayList<Statement> code;
    public ExprArgument condition;

    @Override
    public int execute(Context context) {
        while (true) {
            context.execute(code);
            Constant c = condition.solve(context.instance, context.other);
            if (c.isString) {
                Runner.Error("do {} while (string?) wtf!");
                return 0;
            }
            if (c.dVal <= 0.5)
                break;
        }
        return 0;
    }

}
