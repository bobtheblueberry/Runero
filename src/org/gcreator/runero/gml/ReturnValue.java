package org.gcreator.runero.gml;

public class ReturnValue {
    
    public static enum Type { VARIABLE, ARRAY, SUCCESS, FAILURE};
    public static final ReturnValue SUCCESS = new ReturnValue(Type.SUCCESS);
    public static final ReturnValue FAILURE = new ReturnValue(Type.FAILURE);
    
    public Type type;
    
    public ReturnValue(Type type) {
        this.type = type;
    }
}
