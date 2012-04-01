package org.gcreator.runero.gml.exec;

public class Constant {

    public static int NUMBER = 0;
    public static int STRING = 1;
    
    public final int type;
    
    public String sVal;
    public double dVal;
    
    public Constant(double d) {
        type = NUMBER;
        dVal = d;
    }
    
    public Constant(boolean b) {
        type = NUMBER;
        dVal = b ? 1 : 0;
    }
    
    public Constant(String s) {
        type = STRING;
        sVal = s;
    }
    
    public String toString() {
        if (type == NUMBER)
            return dVal + "";
        else
            return sVal;
    }
}
