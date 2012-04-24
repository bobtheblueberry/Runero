package org.gcreator.runero.gml;

import java.awt.Color;

import org.gcreator.runero.gml.exec.ExprArgument;
import org.gcreator.runero.gml.exec.Variable;
import org.gcreator.runero.inst.Instance;

public class GmlParser {

    /**
     * Some of the Game Maker actions have a 'both' type argument
     * meaning either a string or an expression.
     * Being an expression if it starts with a single ' or double " quote.
     * For example 'X: ' + string(x)
     * 
     * @param expr
     * @return 
     */
    public static String getExpressionString(org.gcreator.runero.event.Argument a, Instance instance, Instance other) {
        if (a.bothIsExpr) {
            VariableVal v = solve(a.exprVal, instance, other);
            if (v.isReal)
                return "" + v.realVal;
            return v.val;
        }
        return a.val;
    }

    public static double getExpression(ExprArgument a, Instance instance, Instance other) {
        try {
            Constant c = a.solve(instance, other);

            if (c == null) {
                System.err.println("Error reading argument: " + a.debugVal);
                return 0;
            }
            if (c.isString) {
                System.err.println("WARNING: String value for non-string expression");
            }
            return c.dVal;
        } catch (Exception exc) {
            System.err.println(instance.obj.getName() + ": Error parsing argument: " + a.expressions.size() + "; " + a.debugVal);
            exc.printStackTrace();
        }
        return 0;
    }

    public static VariableVal solve(ExprArgument a, Instance instance, Instance other) {
        Constant c = a.solve(instance, other);
        VariableVal v = (c.isReal) ? new VariableVal(c.dVal) : new VariableVal(c.sVal);
        return v;
    }

    public static String getArrayName(Variable v, Instance instance, Instance other) {
        //TODO: add some sort of real array
        if (!v.isArray)
            return v.name;
        Constant c = v.arrayIndex.solve(instance, other);
        if (c.isString) {
            System.err.println("Error: Array Index <String>");
            return null;
        }
        int x = (int) c.dVal;
        if (x == 0) {
            return v.name;// How GM does it
        }
        return v.name + "*" + x;
    }
    
    // Thank you LateralGM (IsmAvatar, Clam, Quadduc)
    public static Color convertGmColor(int col) {
        return new Color(col & 0xFF, (col & 0xFF00) >> 8, (col & 0xFF0000) >> 16);
    }

    public static int getGmColor(Color col) {
        return col.getRed() | col.getGreen() << 8 | col.getBlue() << 16;
    }
}
