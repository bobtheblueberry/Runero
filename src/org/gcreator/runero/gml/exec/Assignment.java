package org.gcreator.runero.gml.exec;

import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.gml.VariableManager;

public class Assignment implements Statement {

    public enum OPERATION { // = += -= /= *= %= &= |= ^=
        SET, ADD, SUB, DIVIDE, MULTIPLY, MOD, AND, OR, XOR
    }

    public final OPERATION op;
    public VariableRef variable;
    public ExprArgument value;

    public Assignment(OPERATION op)
        {
            this.op = op;
        }

    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub
        Constant c = value.solve(context.instance, context.other);
        switch (op) {
            case SET:
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case ADD:
                VariableManager.setVariable(variable, c, true, context.instance, context.other);
                break;
            case SUB:
                // TODO: Type checking
                c.dVal = VariableManager.findVariable(variable, context.instance, context.other).realVal - c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case DIVIDE:
                // TODO: Type checking
                c.dVal = VariableManager.findVariable(variable, context.instance, context.other).realVal / c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case MULTIPLY:
                // TODO: Type checking
                c.dVal = VariableManager.findVariable(variable, context.instance, context.other).realVal * c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case MOD:
                // TODO: Type checking
                c.dVal = VariableManager.findVariable(variable, context.instance, context.other).realVal % c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case AND:
                // TODO: Type checking
                c.dVal = (int)VariableManager.findVariable(variable, context.instance, context.other).realVal & (int)c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case OR:
                // TODO: Type checking
                c.dVal = (int)VariableManager.findVariable(variable, context.instance, context.other).realVal | (int)c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
            case XOR:
                // TODO: Type checking
                c.dVal = (int)VariableManager.findVariable(variable, context.instance, context.other).realVal ^ (int)c.dVal;
                VariableManager.setVariable(variable, c, context.instance, context.other);
                break;
        }
    }

}
