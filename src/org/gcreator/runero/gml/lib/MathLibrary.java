package org.gcreator.runero.gml.lib;

import java.util.Arrays;
import java.util.Random;

import org.gcreator.runero.gml.Constant;

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

public class MathLibrary {

    protected static long   seed   = 0;
    protected static Random random = new Random(seed);

    private MathLibrary()
        {
        }

    protected static double[] getReals(Constant... vars) {
        double[] ds = new double[vars.length];
        for (int i = 0; i < vars.length; i++) {
            ds[i] = vars[i].dVal;
        }
        return ds;
    }

    protected static boolean is_real(Constant var) {
        if (var == null || var.sVal == null)
            return false;
        try {
            Double.valueOf(var.sVal);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }

    protected static boolean is_string(Constant val) {
        return val.isString;
    }

    protected static double random(double d) {
        return random.nextDouble() * d;
    }

    protected static void random_set_seed(long s) {
        seed = s;
        random.setSeed(s);
    }

    protected static long random_get_seed() {
        return seed;
    }

    protected static void randomize() {
        seed = random.nextLong();
        random.setSeed(seed);
    }

    protected static Constant choose(Constant... vals) {
        int n = (int) (Math.random() * vals.length);
        return vals[n];
    }

    protected static double abs(double d) {
        return Math.abs(d);
    }

    protected static double round(double d) {
        return Math.round(d);
    }

    protected static double floor(double d) {
        return Math.floor(d);
    }

    protected static double ceil(double d) {
        return Math.ceil(d);
    }

    protected static double sign(double d) {
        return Math.signum(d);
    }

    protected static double frac(double d) {
        return d - Math.floor(d);
    }

    protected static double sqrt(double d) {
        return Math.sqrt(d);
    }

    protected static double sqr(double d) {
        return d * d;
    }

    protected static double exp(double d) {
        return Math.exp(d);
    }

    protected static double ln(double d) {
        return Math.log(d);
    }

    protected static double log2(double d) {
        return Math.log(d) / Math.log(2);
    }

    protected static double log10(double d) {
        return Math.log10(d);
    }

    protected static double logn(double n, double x) {
        return Math.log(n) / Math.log(x);
    }

    protected static double sin(double d) {
        return Math.sin(d);
    }

    protected static double cos(double d) {
        return Math.cos(d);
    }

    protected static double tan(double d) {
        return Math.tan(d);
    }

    protected static double arcsin(double d) {
        return Math.asin(d);
    }

    protected static double arccos(double d) {
        return Math.acos(d);
    }

    protected static double arctan(double d) {
        return Math.atan(d);
    }

    protected static double arctan2(double y, double x) {
        return Math.atan2(y, x);
    }

    protected static double degtorad(double d) {
        return Math.toRadians(d);
    }

    protected static double radtodeg(double d) {
        return Math.toDegrees(d);
    }

    protected static double power(double x, double n) {
        return Math.pow(x, n);
    }

    protected static double min(double... vals) {
        double d = vals[0];
        for (double v : vals) {
            if (v < d)
                d = v;
        }
        return d;
    }

    protected static Constant min(Constant... vars) {
        if (vars[0].isReal) {
            double d = vars[0].dVal;
            for (Constant c : vars) {
                if (c.dVal < d)
                    d = c.dVal;
            }
            return new Constant(d);
        }
        // String
        Constant var = vars[0];
        for (Constant v : vars) {
            if (v.sVal.length() < var.sVal.length())
                var = v;
        }
        return var;
    }

    protected static Constant max(Constant... vars) {
        if (vars[0].isReal) {
            double d = vars[0].dVal;
            for (Constant c : vars) {
                if (c.dVal > d)
                    d = c.dVal;
            }
            return new Constant(d);
        }
        Constant var = vars[0];
        for (Constant v : vars) {
            if (v.sVal.length() > var.sVal.length())
                var = v;
        }
        return var;
    }

    protected static double mean(double... vals) {
        double x = 0;
        for (double d : vals) {
            x += d;
        }
        return x / vals.length;
    }

    protected static double median(double... vals) {
        Arrays.sort(vals);
        if (vals.length % 2 == 0)
            return vals[(vals.length / 2) - 1];
        else
            return vals[((vals.length - 1) / 2) - 1];
    }

    protected static double point_distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(sqr(Math.abs(x2 - x1)) + sqr(Math.abs(y2 - y1)));
    }

    protected static double point_distance_3d(double x1, double y1, double z1, double x2, double y2, double z2) {
        double t1 = Math.sqrt(sqr(Math.abs(x2 - x1)) + sqr(Math.abs(y2 - y1)));
        return Math.sqrt(sqr(t1) + sqr(Math.abs(z2 - z1)));
    }

    protected static double point_direction(double x1, double y1, double x2, double y2) {
        double basic = Math.atan2((y2 - y1), (x2 - x1)) / Math.PI;
        if (y1 < y2)
            return abs(180 + 180 * (1 - basic));
        return abs(180 * basic);
    }

    protected static double lendir_x(double len, double dir) {
        return Math.abs(cos(java.lang.Math.toRadians(dir)) * len);
    }

    protected static double lendir_y(double len, double dir) {
        return Math.abs(sin(java.lang.Math.toRadians(dir)) * len);
    }

    protected static double random_range(double x1, double x2) {
        return random(x2 - x1) + x1;
    }

    protected static double irandom(double x) {
        return (int) random(x);
    }

    protected static double irandom_range(double x1, double x2) {
        return (int) (random(x2 - x1) + x1);
    }

    protected static double dot_product(double x1, double y1, double x2, double y2) {
        return (x1*x2 + y1*y2); // is the right?
    }

    protected static double dot_product_3d(double x1, double y1, double z1, double x2, double y2, double z2) {
        return (x1*x2 + y1*y2 + z1*z2);
    }

    protected static double lerp(double val1, double val2, double amount) {
        return val1 + ((val2 - val1) * amount);
    }

    protected static double clamp(double amount, double min, double max) {
        if (amount > max)
            amount = max;
        if (amount < min)
            amount = min;
        return amount;
    }
}
