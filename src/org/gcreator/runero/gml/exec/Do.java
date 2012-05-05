package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.Runner;
import org.gcreator.runero.gml.Constant;

public class Do implements Statement {

    public ArrayList<Statement> code;
    public ExprArgument condition;

    @Override
    public void execute(Context context) {
        while (true) {
            context.execute(code);
            Constant c = condition.solve(context.instance, context.other);
            if (c.isString) {
                Runner.Error("do {} while (string?) wtf!");
                return;
            }
            if (c.dVal <= 0.5)
                break;
        }
    }

}
