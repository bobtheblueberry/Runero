package org.gcreator.runero.gml;

/**
 * Holds a String or a double
 * 
 * @author serge
 *
 */
public class VariableVal {

    public String val;
    public boolean isString;
    public boolean isReal;
    public double realVal;
    public static final VariableVal ZERO = new VariableVal(0), FALSE = ZERO;
    public static final VariableVal ONE = new VariableVal(1), TRUE = ONE;

    public boolean isTrue() {
        return !isString && realVal == 1;
    }

    public VariableVal(String s)
        {
            this.val = s;
        }

    public void set(Constant c) {
        this.isString = c.isString;
        this.isReal = c.isReal;
        this.val = (isString) ? c.sVal : null;
        this.realVal = (isReal) ? c.dVal : 0;
    }

    public VariableVal(Constant value)
        {
            if (value.isString) {
                this.val = value.sVal;
                isString = true;
            } else {
                this.realVal = value.dVal;
                isReal = true;
            }
        }

    public VariableVal(double val)
        {
            realVal = val;
            isReal = true;
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

    public String toString() {
        if (isString)
            return  val;
        else
            return realVal + "";
    }
}
