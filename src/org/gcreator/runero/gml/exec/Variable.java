package org.gcreator.runero.gml.exec;

public class Variable {
    public String name;
    public boolean isArray;
    public Argument arrayIndex;

    public Variable(String name) {
        this.name = name;
    }
}
