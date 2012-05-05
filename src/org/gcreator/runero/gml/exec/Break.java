package org.gcreator.runero.gml.exec;

public class Break implements Statement {

    @Override
    public void execute(Context context) {
        context.breakLoop();
    }

}
