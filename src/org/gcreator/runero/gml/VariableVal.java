package org.gcreator.runero.gml;

/**
 * Holds a String or a double
 * 
 * @author serge
 *
 */
public class VariableVal extends ReturnValue {
    
    public String val;
    public boolean isString = true;
    public boolean isReal = false;
    public double realVal = 0;
    
    public boolean isTrue() {
        return !isString && realVal == 1;
    }
    
    public VariableVal() {
        super(Type.VARIABLE);
    }
    
    public VariableVal(String s) {
        super(Type.VARIABLE);
        this.val = s;
    }
    
    public static VariableVal Real(double val) {
        VariableVal v = new VariableVal();
        v.realVal = val;
        v.isReal = true;
        v.val = null;
        v.isString = false;
        return v;
    }
    
    public static VariableVal Bool(boolean val) {
        return Real(val ? 1 : 0);
    }
}
