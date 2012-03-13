package org.gcreator.runero.gml.lex;

import java.util.ArrayList;

public class Function {
    public String name;
    public ArrayList<Argument> args;
    
    public Function(String name) {
        this.name = name;
        args = new ArrayList<Argument>();
    }
}
