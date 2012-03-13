package org.gcreator.runero.gml.lex;

public class Expression {

    public static enum Type {
        NUMBER, // number constant
        STRING, // string constant
        VARIABLE, // x, object0.x
        FUNCTION, // name_name_x(args..)
        PARENTHESIS,
        OPERATOR
    };
    
    public Type type;

    public Constant constant; // string/number constants
    public Variable variable; // variables
    public Function function; // used for function types
    public Argument parenthesis; // used for parenthesis types
    public Operator op; // Used for operator types
    
    
    public Expression (Type type) {
        this.type = type;
    }
    
    public Expression (Constant c) {
        this.type = (c.type == Constant.NUMBER) ? Type.NUMBER : Type.STRING;
        constant = c;
    }
    
    public Expression (Variable var) {
        this.type = Type.VARIABLE;
        variable = var;
    }
    
    public Expression (Function f) {
        this.type = Type.FUNCTION;
        function = f;
    }
    
    public Expression(Argument par) {
        this.type = Type.PARENTHESIS;
        parenthesis = par;
    }
    
    public Expression(Operator op) {
        this.type = Type.OPERATOR;
        this.op = op;
    }
}
