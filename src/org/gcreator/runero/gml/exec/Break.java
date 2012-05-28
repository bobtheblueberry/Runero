package org.gcreator.runero.gml.exec;

public class Break implements Statement {

    @Override
    public int execute(Context context) {
        return Context.BREAK;
    }

}
