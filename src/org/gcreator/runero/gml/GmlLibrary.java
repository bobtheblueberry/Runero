package org.gcreator.runero.gml;

import java.util.ArrayList;

import org.gcreator.runero.gml.lib.MathLibrary;

public class GmlLibrary {
    private ArrayList<FunctionLibrary> libs;
    
    public GmlLibrary() {
        libs = new ArrayList<FunctionLibrary>();
        libs.add(MathLibrary.lib);
    }
    
    public ReturnValue executeFunction(String function, VariableVal... args) {
        ReturnValue rv = null;
        for (FunctionLibrary l : libs) {
            rv = l.getFunction(function, args);
            if (rv != null) {
                return rv;
            }
        }
        return null;
    }
}
