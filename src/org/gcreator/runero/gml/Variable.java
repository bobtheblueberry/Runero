package org.gcreator.runero.gml;

/**
 * Holds a String or a double
 * 
 * @author serge
 *
 */
public class Variable extends ReturnValue {
    
    public String val;
    public boolean isString = true;
    public boolean isReal = false;
    public double realVal = 0;
    
    public boolean isTrue() {
        return !isString && realVal == 1;
    }
    
    public Variable() {
        super(Type.VARIABLE);
    }
    
    public Variable(String s) {
        super(Type.VARIABLE);
        this.val = s;
    }
    
    public static Variable Real(double val) {
        Variable v = new Variable();
        v.realVal = val;
        v.isReal = true;
        v.val = null;
        v.isString = false;
        return v;
    }
    
    public static Variable Bool(boolean val) {
        return Real(val ? 1 : 0);
    }
}
