package org.gcreator.runero.gml;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gcreator.runero.gml.exec.ExprArgument;
import org.gcreator.runero.inst.Instance;

public class GmlParser {

    public static void setVariable(String name, String value, boolean relative, Instance instance, Instance other) {

        // first check to see if it is a defined variable
        // then check to see if its a constant * variable

        // name could be like other.x

    }

    public static VariableVal getVariable(String name, Instance instance, Instance other) {

        // first check to see if it is a defined variable
        // then check to see if its a constant * variable

        // name could be like other.x
        return new VariableVal("Josh Write me a Parser");
    }

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
        return 0;
    }

    public static VariableVal solve(ExprArgument a, Instance Instance, Instance other) {

        return new VariableVal("nope");
    }

    // Thank you LateralGM (IsmAvatar, Clam, Quadduc)
    public static Color convertGmColor(int col) {
        return new Color(col & 0xFF, (col & 0xFF00) >> 8, (col & 0xFF0000) >> 16);
    }

    public static int getGmColor(Color col) {
        return col.getRed() | col.getGreen() << 8 | col.getBlue() << 16;
    }
}
