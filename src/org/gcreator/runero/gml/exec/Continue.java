package org.gcreator.runero.gml.exec;

public class Continue implements Statement {

    @Override
    public int execute(Context context) {
        return Context.CONTINUE;
    }

}
