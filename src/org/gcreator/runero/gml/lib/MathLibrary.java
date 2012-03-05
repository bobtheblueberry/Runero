package org.gcreator.runero.gml.lib;

import java.util.Arrays;
import java.util.Random;

import org.gcreator.runero.gml.FunctionLibrary;
import org.gcreator.runero.gml.Variable;

/*
// section 2.2

is_real(val)
is_string(val)
random(x)
random_set_seed(seed)
random_get_seed()
randomize()
choose(x1,x2,x3,...)
abs(x)
round(x)
floor(x)
ceil(x)
sign(x)
frac(x)
sqrt(x)
sqr(x)
exp(x)
ln(x)
log2(x)
log10(x)
sin(x)
cos(x)
tan(x)
arcsin(x)
arccos(x)
arctan(x)
arctan2(y,x)
degtorad(x)
radtodeg(x)
power(x,n)
logn(n,x)
min(x1,x2,x3,...)
max(x1,x2,x3,...)
mean(x1,x2,x3,...)
median(x1,x2,x3,...)
point_distance(x1,y1,x2,y2)
point_direction(x1,y1,x2,y2)
lengthdir_x(len,dir)
lengthdir_y(len,dir)
*/

@SuppressWarnings("unused")
public class MathLibrary extends FunctionLibrary {

    private static long seed = 0;
    private static Random random = new Random(seed);

    @Override
    public Variable getFunction(String fn, Variable... args) {
        // Check to see if some dumbo gave us strings
        // they are only allowed for min, max, is_real, and is_string
        if (args.length > 1 && !fn.equals("min") && !fn.equals("max") &&
                !fn.equals("is_real") && !fn.equals("is_string") && !fn.equals("choose"))
            for (Variable v : args) {
                if (!v.isReal) {
                    System.err.println("Math Library given String for function " + fn);
                    return null;
                }
            }
        char c = fn.charAt(0);
        double arg0 = args[0].realVal;
        double arg1 = 0, arg2 = 0, arg3 = 0;
        if (args.length > 1) {
            arg1 = args[1].realVal;
            if (args.length > 2) {
                arg2 = args[2].realVal;
                if (args.length > 3)
                    arg3 = args[3].realVal;
            }
        }
        switch (c) {
        case 'a':
            if (fn.equals("abs")) {
                return Variable.Real(abs(arg0));
            }
            if (fn.equals("arcsin")) {
                return Variable.Real(arcsin(arg0));
            }
            if (fn.equals("arccos")) {
                return Variable.Real(arccos(arg0));
            }
            if (fn.equals("arctan")) {
                return Variable.Real(arctan(arg0));
            }
            if (fn.equals("arctan2")) {
                return Variable.Real(arctan2(arg0, arg1));
            }

            break;
        case 'c':

            if (fn.equals("ceil")) {
                return Variable.Real(ceil(arg0));
            }
            if (fn.equals("choose")) {
                return choose(args);
            }
            if (fn.equals("cos")) {
                return Variable.Real(cos(arg0));
            }
            break;
        case 'd':

            if (fn.equals("degtorad")) {
                return Variable.Real(degtorad(arg0));
            }
            break;
        case 'e':
            if (fn.equals("exp")) {
                return Variable.Real(exp(arg0));
            }
            break;
        case 'f':
            if (fn.equals("floor")) {
                return Variable.Real(floor(arg0));
            }
            if (fn.equals("frac")) {
                return Variable.Real(frac(arg0));
            }
            break;
        case 'i':
            if (fn.equals("is_real")) {
                return Variable.Bool(is_real(args[0]));
            }
            if (fn.equals("is_string")) {
                return Variable.Bool(is_string(args[0]));
            }
            break;
        case 'l':
            if (fn.equals("lendir_x")) {
                return Variable.Real(lendir_x(arg0, arg1));
            }
            if (fn.equals("lendir_y")) {
                return Variable.Real(lendir_y(arg0, arg1));
            }
            if (fn.equals("ln")) {
                return Variable.Real(ln(arg0));
            }
            if (fn.equals("log10")) {
                return Variable.Real(log10(arg0));
            }
            if (fn.equals("log2")) {
                return Variable.Real(log2(arg0));
            }
            if (fn.equals("logn")) {
                return Variable.Real(logn(arg0, arg1));
            }
            break;
        case 'm':
            if (fn.equals("max")) {

            }
            if (fn.equals("min")) {

            }
            if (fn.equals("mean")) {

            }
            if (fn.equals("median")) {

            }
            break;
        case 'p':
            if (fn.equals("point_direction")) {

            }
            if (fn.equals("point_distance")) {

            }
            if (fn.equals("power")) {

            }
            break;
        case 'r':
            if (fn.equals("random")) {

            }
            if (fn.equals("radtodeg")) {

            }
            if (fn.equals("random_get_seed")) {

            }
            if (fn.equals("random_set_seed")) {

            }
            if (fn.equals("randomize")) {

            }
            if (fn.equals("round")) {

            }
            break;
        case 's':
            if (fn.equals("sign")) {

            }
            if (fn.equals("sin")) {

            }
            if (fn.equals("sqr")) {

            }
            if (fn.equals("sqrt")) {

            }
            break;
        case 't':
            if (fn.equals("tan")) {

            }
            break;
        }
        return null;
    }

    private boolean is_real(Variable var) {
        if (var == null || var.val == null)
            return false;
        try {
            Double.valueOf(var.val);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    private boolean is_string(Variable val) {
        return val.isString;
    }

    private double random(double d) {
        return random.nextDouble() * d;
    }

    private void random_set_seed(int s) {
        seed = s;
        random.setSeed(s);
    }

    // WARNING: badly implemented function
    private long random_get_seed() {
        return seed;
    }

    private void randomize() {
        seed = random.nextLong();
        random.setSeed(seed);
    }

    private Variable choose(Variable... vals) {
        int n = (int) (Math.random() * vals.length);
        return vals[n];
    }

    private double abs(double d) {
        return Math.abs(d);
    }

    private double round(double d) {
        return Math.round(d);
    }

    private double floor(double d) {
        return Math.floor(d);
    }

    private double ceil(double d) {
        return Math.ceil(d);
    }

    private double sign(double d) {
        return Math.signum(d);
    }

    private double frac(double d) {
        return d - Math.floor(d);
    }

    private double sqrt(double d) {
        return Math.sqrt(d);
    }

    private double sqr(double d) {
        return d * d;
    }

    private double exp(double d) {
        return Math.exp(d);
    }

    private double ln(double d) {
        return Math.log(d);
    }

    private double log2(double d) {
        return Math.log(d) / Math.log(2);
    }

    private double log10(double d) {
        return Math.log10(d);
    }

    private double logn(double n, double x) {
        return Math.log(n) / Math.log(x);
    }

    private double sin(double d) {
        return Math.sin(d);
    }

    private double cos(double d) {
        return Math.cos(d);
    }

    private double tan(double d) {
        return Math.tan(d);
    }

    private double arcsin(double d) {
        return Math.asin(d);
    }

    private double arccos(double d) {
        return Math.acos(d);
    }

    private double arctan(double d) {
        return Math.atan(d);
    }

    private double arctan2(double y, double x) {
        return Math.atan2(y, x);
    }

    private double degtorad(double d) {
        return Math.toRadians(d);
    }

    private double radtodeg(double d) {
        return Math.toDegrees(d);
    }

    private double power(double x, double n) {
        return Math.pow(x, n);
    }

    private double min(double... vals) {
        double d = vals[0];
        for (double v : vals) {
            if (v < d)
                d = v;
        }
        return d;
    }

    private Variable minStr(Variable... vars) {
        Variable var = vars[0];
        for (Variable v : vars) {
            if (v.val.length() < var.val.length())
                var = v;
        }
        return var;
    }

    private double max(double... vals) {
        double d = vals[0];
        for (double v : vals) {
            if (v > d)
                d = v;
        }
        return d;
    }

    private Variable maxStr(Variable... vars) {
        Variable var = vars[0];
        for (Variable v : vars) {
            if (v.val.length() > var.val.length())
                var = v;
        }
        return var;
    }

    private double mean(double... vals) {
        double x = 0;
        for (double d : vals) {
            x += d;
        }
        return x / vals.length;
    }

    private double median(double... vals) {
        Arrays.sort(vals);
        if (vals.length % 2 == 0)
            return vals[(vals.length / 2) - 1];
        else
            return vals[((vals.length - 1) / 2) - 1];
    }

    private double point_distance(double x1, double y1, double x2, double y2) {
        return java.lang.Math.sqrt(sqr(Math.abs(x2 - x1)) + sqr(Math.abs(y2 - y1)));
    }

    private double point_direction(double x1, double y1, double x2, double y2) {
        double basic = java.lang.Math.atan2((y2 - y1), (x2 - x1)) / Math.PI;
        if (y1 < y2)
            return abs(180 + 180 * (1 - basic));
        return abs(180 * basic);
    }

    private double lendir_x(double len, double dir) {
        return Math.abs(cos(java.lang.Math.toRadians(dir)) * len);
    }

    private double lendir_y(double len, double dir) {
        return Math.abs(sin(java.lang.Math.toRadians(dir)) * len);
    }
}
