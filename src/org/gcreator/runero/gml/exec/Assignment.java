package org.gcreator.runero.gml.exec;

public class Assignment implements Statement {

    public enum OPERATION { // = += -= /= *= %= &= |= ^=
        SET, ADD, SUB, DIVIDE, MULTIPLY, MOD, AND, OR, XOR 
    }
    
    public final OPERATION op;
    public VariableRef variable;
    public ExprArgument value;
    
    public Assignment(OPERATION op) {
        this.op = op;
    }
    @Override
    public void execute(Context context) {
        // TODO Auto-generated method stub

    }

}
