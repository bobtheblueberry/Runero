package org.gcreator.runero.gml;

import org.gcreator.runero.gml.exec.Constant;

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
    public static final VariableVal ZERO = Real(0);
    public static final VariableVal ONE = Real(1);

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

    public void set(Constant c) {
        this.isString = c.type == Constant.STRING;
        this.isReal = c.type == Constant.NUMBER;
        this.val = (isString) ? c.sVal : null;
        this.realVal = (isReal) ? c.dVal : 0;
    }

    public VariableVal(Constant value) {
        super(Type.VARIABLE);
        if (value.type == Constant.STRING) {
            this.val = value.sVal;
            isString = true;
        } else {
            this.realVal = value.dVal;
            isReal = true;
        }
    }

    public static VariableVal Real(double val) {
        VariableVal v = new VariableVal();
        v.realVal = val;
        v.isReal = true;
        return v;
    }

    public static VariableVal Bool(boolean val) {
        return val ? ONE : ZERO;
    }

    public Constant getConstant() {
        return (isReal) ? new Constant(realVal) : new Constant(val);
    }

    public boolean equals(Object o) {
        if (o instanceof VariableVal) {
            VariableVal other = (VariableVal) o;
            return isString == other.isString && isReal == other.isReal
                    && (isReal ? realVal == other.realVal : val.equals(other.val));
        }
        return false;
    }
}
