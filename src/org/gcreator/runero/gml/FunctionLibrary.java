package org.gcreator.runero.gml;

public abstract class FunctionLibrary {

    public abstract ReturnValue getFunction(String function, VariableVal... args);
    
}
