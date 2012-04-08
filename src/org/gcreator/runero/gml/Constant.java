package org.gcreator.runero.gml;

public class Constant {

    public static int NUMBER = 0;
    public static int STRING = 1;
    
    public final int type;
    public final boolean isReal;
    public final boolean isString;
    public String sVal;
    public double dVal;
    
    public static final Constant ONE = new Constant(1), TRUE = ONE;
    public static final Constant ZERO = new Constant(0), FALSE = ZERO;
    
    public Constant(double d) {
        type = NUMBER;
        isReal = true;
        isString = false;
        dVal = d;
    }
    
    public Constant(boolean b) {
        type = NUMBER;
        isReal = true;
        isString = false;
        dVal = b ? 1 : 0;
    }
    
    public Constant(String s) {
        type = STRING;
        isString = true;
        isReal = false;
        sVal = s;
    }
    
    public String toString() {
        if (type == NUMBER)
            return dVal + "";
        else
            return sVal;
    }
}
