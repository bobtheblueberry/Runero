package org.gcreator.runero.gml.exec;

import java.util.ArrayList;
import java.util.LinkedList;

import org.gcreator.runero.Runner;
import org.gcreator.runero.gml.VariableManager;
import org.gcreator.runero.gml.VariableVal;
import org.gcreator.runero.gml.exec.Expression.Type;
import org.gcreator.runero.inst.Instance;

/**
 * An argument is a series of expressions
 * 
 * @author serge
 * 
 */
public class ExprArgument {
    public ArrayList<Expression> expressions;
    public String debugVal = "";

    public ExprArgument() {
        expressions = new ArrayList<Expression>();
    }

    public void add(Expression e) {
        expressions.add(e);
    }

    int i;

    public Constant solve() {
        // TODO: variable and function system
        return solve(null, null);
    }

    public Constant solve(Instance instance, Instance other) {
        if (expressions.size() == 0)
            return null;
        Constant c = null;
        LinkedList<Thing> exp = new LinkedList<ExprArgument.Thing>();
        for (Expression e : expressions) {
            if (e.type == Type.NUMBER || e.type == Type.STRING) {
                exp.add(new Thing(e.constant));
            } else if (e.type == Type.VARIABLE) {
                VariableVal v = VariableManager.findVariable(e.variable, instance, other);
                if (v == null) {
                    Runner.Error("Unknown variable " + e.variable);
                    continue;
                }
                exp.add(new Thing(v.getConstant()));
            } else if (e.type == Type.FUNCTION) {
                // TODO: solve function
                exp.add(new Thing(new Constant(0)));
            } else if (e.type == Type.PARENTHESIS) {
                exp.add(new Thing(e.parenthesis.solve()));
            } else if (e.type == Type.OPERATOR) {
                exp.add(new Thing(e.op));
            }
        }

        for (int i = 0; i < exp.size(); i++) {
            Thing t = exp.get(i);
            if (!t.isOp)
                continue;
            if (t.op == Operator.NEGATE) {
                exp.remove(i);
                Thing n = exp.get(i);
                n.c.dVal = -n.c.dVal;
            } else if (t.op == Operator.NOT) {
                exp.remove(i);
                Thing n = exp.get(i);
                if (n.c.dVal == 0)
                    n.c.dVal = 1;
                else
                    n.c.dVal = 0;
            } else if (t.op == Operator.BITW_INVERT) {
                exp.remove(i);
                Thing n = exp.get(i);
                n.c.dVal = ~Math.round(n.c.dVal);
            }
        }

        performOp(Operator.MULTIPLY, exp);
        performOp(Operator.DIVIDE, exp);
        performOp(Operator.INT_DIVIDE, exp);
        performOp(Operator.MODULO, exp);
        performOp(Operator.PLUS, exp);
        performOp(Operator.MINUS, exp);
        performOp(Operator.BITW_LEFT, exp);
        performOp(Operator.BITW_RIGHT, exp);
        performOp(Operator.GREATER, exp);
        performOp(Operator.GREATER_EQUAL, exp);
        performOp(Operator.LESS, exp);
        performOp(Operator.LESS_EQUAL, exp);
        performOp(Operator.EQUALS, exp);
        performOp(Operator.NOT_EQUAL, exp);
        performOp(Operator.BITW_AND, exp);
        performOp(Operator.BITW_OR, exp);
        performOp(Operator.BITW_XOR, exp);
        performOp(Operator.AND, exp);
        performOp(Operator.OR, exp);
        performOp(Operator.XOR, exp);
        if (exp.size() > 1) {
            System.err.println("WHAT?? " + exp);
        } else {
            c = exp.get(0).c;
        }
        return c;
    }

    private void performOp(Operator op, LinkedList<Thing> exp) {
        for (int i = 0; i < exp.size(); i++) {
            Thing t = exp.get(i);
            if (!t.isOp)
                continue;
            if (t.op != op)
                continue;
            Thing p = exp.get(i - 1);
            Thing n = exp.get(i + 1);
            // TODO: value type checking
            if (t.op == Operator.MULTIPLY) {
                t = new Thing(new Constant(p.c.dVal * n.c.dVal));
            } else if (t.op == Operator.DIVIDE) {
                t = new Thing(new Constant(p.c.dVal / n.c.dVal));
            } else if (t.op == Operator.INT_DIVIDE) {
                t = new Thing(new Constant((int) (p.c.dVal / n.c.dVal)));
            } else if (t.op == Operator.MODULO) {
                t = new Thing(new Constant(p.c.dVal % n.c.dVal));
            } else if (t.op == Operator.PLUS) {
                // Game Maker bitches if you try to do number + string but we don't need to
                if (p.c.type == Constant.STRING || n.c.type == Constant.STRING) {
                    String s = "" + ((p.c.type == Constant.STRING) ? p.c.sVal : p.c.dVal)
                            + ((n.c.type == Constant.STRING) ? n.c.sVal : n.c.dVal);
                    t = new Thing(new Constant(s));
                } else
                    t = new Thing(new Constant(p.c.dVal + n.c.dVal));
            } else if (t.op == Operator.MINUS) {
                t = new Thing(new Constant(p.c.dVal - n.c.dVal));
            } else if (t.op == Operator.BITW_LEFT) {
                t = new Thing(new Constant(Math.round(p.c.dVal) << Math.round(n.c.dVal)));
            } else if (t.op == Operator.BITW_RIGHT) {
                t = new Thing(new Constant(Math.round(p.c.dVal) >> Math.round(n.c.dVal)));
            } else if (t.op == Operator.GREATER) {
                t = new Thing(getBool(p.c.dVal > n.c.dVal));
            } else if (t.op == Operator.GREATER_EQUAL) {
                t = new Thing(getBool(p.c.dVal >= n.c.dVal));
            } else if (t.op == Operator.LESS) {
                t = new Thing(getBool(p.c.dVal < n.c.dVal));
            } else if (t.op == Operator.LESS_EQUAL) {
                t = new Thing(getBool(p.c.dVal <= n.c.dVal));
            } else if (t.op == Operator.EQUALS) {
            boolean strp = p.c.type == Constant.STRING;
            boolean strn = n.c.type == Constant.STRING;
            if (strp ^ strn)
                t = new Thing(getBool(false));
            else if (strp && strn)
                t = new Thing(getBool(p.c.sVal.equals(n.c.sVal)));
            else
                t = new Thing(getBool(p.c.dVal == n.c.dVal));
            } else if (t.op == Operator.NOT_EQUAL) {
                boolean strp = p.c.type == Constant.STRING;
                boolean strn = n.c.type == Constant.STRING;
                if (strp ^ strn)
                    t = new Thing(getBool(true));
                else if (strp && strn)
                    t = new Thing(getBool(!p.c.sVal.equals(n.c.sVal)));
                else
                    t = new Thing(getBool(p.c.dVal != n.c.dVal));
            } else if (t.op == Operator.BITW_AND) {
                t = new Thing(new Constant(Math.round(p.c.dVal) & Math.round(n.c.dVal)));
            } else if (t.op == Operator.BITW_XOR) {
                t = new Thing(new Constant(Math.round(p.c.dVal) ^ Math.round(n.c.dVal)));
            } else if (t.op == Operator.BITW_OR) {
                t = new Thing(new Constant(Math.round(p.c.dVal) | Math.round(n.c.dVal)));
            } else if (t.op == Operator.AND) {
                t = new Thing(new Constant(getBool(p.c.dVal) && getBool(n.c.dVal)));
            } else if (t.op == Operator.OR) {
                t = new Thing(new Constant(getBool(p.c.dVal) || getBool(n.c.dVal)));
            } else if (t.op == Operator.XOR) {
                t = new Thing(new Constant(getBool(p.c.dVal) ^ getBool(n.c.dVal)));
            }
            exp.add(i, t);
            exp.remove(i + 1);
            exp.remove(i + 1);
            exp.remove(i - 1);
            i--;
        }
    }

    private Constant getBool(boolean b) {
        Constant c;
        if (b)
            c = new Constant(1);
        else
            c = new Constant(0);
        return c;
    }

    private boolean getBool(double d) {
        if (d == 1)
            return true;
        return false;
    }

    static class Thing {
        boolean isOp;

        public Thing(Constant c) {
            this.c = c;
        }

        public Thing(Operator op) {
            this.op = op;
            isOp = true;
        }

        public String toString() {
            if (isOp)
                return "" + op;
            else
                return c.toString();
        }

        Constant c;
        Operator op;
    }
}
