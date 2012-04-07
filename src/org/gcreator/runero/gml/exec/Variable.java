package org.gcreator.runero.gml.exec;

public class Variable {
    public String name;
    public boolean isArray;
    public ExprArgument arrayIndex;
    
    public boolean isExpression;
    public ExprArgument expression;
    
    public String toString() {
        if (isExpression) {
            return "(Expr) " + expression;
        } else if (isArray) {
            return "(Array) " + name + "(" + arrayIndex + ")";
        } else
            return (name == null) ? "<null>" : name;
    }
}
