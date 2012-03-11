package org.gcreator.runero.gml.lib;

import java.util.Arrays;
import java.util.Random;

import org.gcreator.runero.gml.FunctionLibrary;
import org.gcreator.runero.gml.ReturnValue;
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

public class MathLibrary extends FunctionLibrary {

    public static MathLibrary lib = new MathLibrary();
    private static long seed = 0;
    private static Random random = new Random(seed);
    
    private MathLibrary() {}

    public ReturnValue getFunction(String fn, Variable... args) {
        // Check to see if some dumbo gave us strings
        // they are only allowed for min, max, is_real, and is_string
        if (args.length > 1 && !fn.equals("min") && !fn.equals("max") && !fn.equals("is_real")
                && !fn.equals("is_string") && !fn.equals("choose"))
            for (Variable v : args) {
                if (!v.isReal) {
                    System.err.println("Math Library given String for function " + fn);
                    return null;
                }
            }
        char c = fn.charAt(0);
        // random_get_seed() and randomize() does not have an argument
        double arg0 = 0, arg1 = 0, arg2 = 0, arg3 = 0;
        if (args.length > 0) {
            arg0 = args[0].realVal;
            if (args.length > 1) {
                arg1 = args[1].realVal;
                if (args.length > 2) {
                    arg2 = args[2].realVal;
                    if (args.length > 3)
                        arg3 = args[3].realVal;
                }
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
                if (args[0].isString) {
                    return maxStr(args);
                } else {
                    return Variable.Real(max(getReals(args)));
                }
            }
            if (fn.equals("min")) {
                if (args[0].isString) {
                    return minStr(args);
                } else {
                    return Variable.Real(min(getReals(args)));
                }
            }
            if (fn.equals("mean")) {
                return Variable.Real(mean(arg0));
            }
            if (fn.equals("median")) {
                return Variable.Real(median(arg0));
            }
            break;
        case 'p':
            if (fn.equals("point_direction")) {
                return Variable.Real(point_direction(arg0, arg1, arg2, arg3));
            }
            if (fn.equals("point_distance")) {
                return Variable.Real(point_distance(arg0, arg1, arg2, arg3));
            }
            if (fn.equals("power")) {
                return Variable.Real(power(arg0, arg1));
            }
            break;
        case 'r':
            if (fn.equals("random")) {
                return Variable.Real(random(arg0));
            }
            if (fn.equals("radtodeg")) {
                return Variable.Real(radtodeg(arg0));
            }
            if (fn.equals("random_get_seed")) {
                return Variable.Real(Double.longBitsToDouble(random_get_seed()));
            }
            if (fn.equals("random_set_seed")) {
                random_set_seed(Double.doubleToLongBits(arg0));
                return ReturnValue.SUCCESS;
            }
            if (fn.equals("randomize")) {
                randomize();
                return ReturnValue.SUCCESS;
            }
            if (fn.equals("round")) {
                return Variable.Real(round(arg0));
            }
            break;
        case 's':
            if (fn.equals("sign")) {
                return Variable.Real(sign(arg0));
            }
            if (fn.equals("sin")) {
                return Variable.Real(sin(arg0));
            }
            if (fn.equals("sqr")) {
                return Variable.Real(sqr(arg0));
            }
            if (fn.equals("sqrt")) {
                return Variable.Real(sqrt(arg0));
            }
            break;
        case 't':
            if (fn.equals("tan")) {
                return Variable.Real(tan(arg0));
            }
            break;
        }
        return null;
    }

    private static double[] getReals(Variable... vars) {
        double[] ds = new double[vars.length];
        for (int i = 0; i < vars.length; i++) {
            ds[i] = vars[i].realVal;
        }
        return ds;
    }

    private static boolean is_real(Variable var) {
        if (var == null || var.val == null)
            return false;
        try {
            Double.valueOf(var.val);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    private static boolean is_string(Variable val) {
        return val.isString;
    }

    private static double random(double d) {
        return random.nextDouble() * d;
    }

    private static void random_set_seed(long s) {
        seed = s;
        random.setSeed(s);
    }

    private static long random_get_seed() {
        return seed;
    }

    private static void randomize() {
        seed = random.nextLong();
        random.setSeed(seed);
    }

    private static Variable choose(Variable... vals) {
        int n = (int) (Math.random() * vals.length);
        return vals[n];
    }

    private static double abs(double d) {
        return Math.abs(d);
    }

    private static double round(double d) {
        return Math.round(d);
    }

    private static double floor(double d) {
        return Math.floor(d);
    }

    private static double ceil(double d) {
        return Math.ceil(d);
    }

    private static double sign(double d) {
        return Math.signum(d);
    }

    private static double frac(double d) {
        return d - Math.floor(d);
    }

    private static double sqrt(double d) {
        return Math.sqrt(d);
    }

    private static double sqr(double d) {
        return d * d;
    }

    private static double exp(double d) {
        return Math.exp(d);
    }

    private static double ln(double d) {
        return Math.log(d);
    }

    private static double log2(double d) {
        return Math.log(d) / Math.log(2);
    }

    private static double log10(double d) {
        return Math.log10(d);
    }

    private static double logn(double n, double x) {
        return Math.log(n) / Math.log(x);
    }

    private static double sin(double d) {
        return Math.sin(d);
    }

    private static double cos(double d) {
        return Math.cos(d);
    }

    private static double tan(double d) {
        return Math.tan(d);
    }

    private static double arcsin(double d) {
        return Math.asin(d);
    }

    private static double arccos(double d) {
        return Math.acos(d);
    }

    private static double arctan(double d) {
        return Math.atan(d);
    }

    private static double arctan2(double y, double x) {
        return Math.atan2(y, x);
    }

    private static double degtorad(double d) {
        return Math.toRadians(d);
    }

    private static double radtodeg(double d) {
        return Math.toDegrees(d);
    }

    private static double power(double x, double n) {
        return Math.pow(x, n);
    }

    private static double min(double... vals) {
        double d = vals[0];
        for (double v : vals) {
            if (v < d)
                d = v;
        }
        return d;
    }

    private static Variable minStr(Variable... vars) {
        Variable var = vars[0];
        for (Variable v : vars) {
            if (v.val.length() < var.val.length())
                var = v;
        }
        return var;
    }

    private static double max(double... vals) {
        double d = vals[0];
        for (double v : vals) {
            if (v > d)
                d = v;
        }
        return d;
    }

    private static Variable maxStr(Variable... vars) {
        Variable var = vars[0];
        for (Variable v : vars) {
            if (v.val.length() > var.val.length())
                var = v;
        }
        return var;
    }

    private static double mean(double... vals) {
        double x = 0;
        for (double d : vals) {
            x += d;
        }
        return x / vals.length;
    }

    private static double median(double... vals) {
        Arrays.sort(vals);
        if (vals.length % 2 == 0)
            return vals[(vals.length / 2) - 1];
        else
            return vals[((vals.length - 1) / 2) - 1];
    }

    public static double point_distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(sqr(Math.abs(x2 - x1)) + sqr(Math.abs(y2 - y1)));
    }

    public static double point_direction(double x1, double y1, double x2, double y2) {
        double basic = Math.atan2((y2 - y1), (x2 - x1)) / Math.PI;
        if (y1 < y2)
            return abs(180 + 180 * (1 - basic));
        return abs(180 * basic);
    }

    private static double lendir_x(double len, double dir) {
        return Math.abs(cos(java.lang.Math.toRadians(dir)) * len);
    }

    private static double lendir_y(double len, double dir) {
        return Math.abs(sin(java.lang.Math.toRadians(dir)) * len);
    }
}
