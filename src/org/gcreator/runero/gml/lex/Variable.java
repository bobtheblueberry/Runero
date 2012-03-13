package org.gcreator.runero.gml.lex;

public class Variable {
    
    public String name;
    public Variable child; // for obj_null.y etc.
    
    public boolean isArray;
    public Argument arrayIndex;
    
    public Variable(String name) {
        this.name = name;
    }

}
