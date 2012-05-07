package org.gcreator.runero.gml.exec;

public class Return implements Statement {

    public ExprArgument value;

    @Override
    public void execute(Context context) {
        context.returnStatement((value == null) ? null : value.solve(context.instance, context.other));
    }

}
