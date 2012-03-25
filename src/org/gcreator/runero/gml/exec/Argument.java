package org.gcreator.runero.gml.exec;

import java.util.ArrayList;


/**
 * An argument is a series of expressions
 * 
 * @author serge
 * 
 */
public class Argument {
    public ArrayList<Expression> expressions;

    public Argument() {
        expressions = new ArrayList<Expression>();
    }
    
    public void add(Expression e) {
        expressions.add(e);
    }
}
