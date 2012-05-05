package org.gcreator.runero.gml.exec;

public class Continue implements Statement {

    @Override
    public void execute(Context context) {
        context.continueLoop();
    }

}
