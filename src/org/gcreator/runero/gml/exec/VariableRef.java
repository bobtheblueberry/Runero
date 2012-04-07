package org.gcreator.runero.gml.exec;

import java.util.ArrayList;

public class VariableRef {

    public ArrayList<Variable> ref;

    public VariableRef() {
        ref = new ArrayList<Variable>();
    }
    
    public String toString() {
        if (ref.isEmpty())
            return "<Null var>";
        String s = ref.get(0).name;
        for (int i = 1; i < ref.size(); i++)
            s = s + "." + ref.get(i).name;
        return s;
    }
}
