package org.gcreator.runero.gml;

public class Constant {
    
    public final boolean isReal;
    public final boolean isString;
    public String sVal;
    public double dVal;
    
    public static final Constant ONE = new Constant(1), TRUE = ONE;
    public static final Constant ZERO = new Constant(0), FALSE = ZERO;
    
    public Constant(double d) {
        isReal = true;
        isString = false;
        dVal = d;
    }
    
    public Constant(boolean b) {
        isReal = true;
        isString = false;
        dVal = b ? 1 : 0;
    }
    
    public Constant(String s) {
        isString = true;
        isReal = false;
        sVal = s;
    }
    
    public String toString() {
        if (isReal)
            return dVal + "";
        else
            return sVal;
    }
}
