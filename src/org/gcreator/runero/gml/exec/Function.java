package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.gml.lib.FunctionManager;
import org.gcreator.runero.inst.Instance;


public class Function implements Statement {
    public String name;
    public int id = -1;
    public ArrayList<ExprArgument> args;
    
    public Function(String name) {
        this.name = name;
        args = new ArrayList<ExprArgument>();
        id = FunctionManager.getId(name);
    }

    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }
    
    public Constant solve(Instance instance, Instance other) {
        // TODO: GML Scripts
        if (id < 0) {
            return null;
        }
        Constant[] c = new Constant[args.size()];
        int i = 0;
        for (ExprArgument arg : args)
            c[i++] = arg.solve(instance, other);
        return FunctionManager.getFunction(id, c);
    }
}
