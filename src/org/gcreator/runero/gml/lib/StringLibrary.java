package org.gcreator.runero.gml.lib;

import java.text.DecimalFormat;
import java.text.FieldPosition;

import org.gcreator.runero.gml.Constant;

public abstract class StringLibrary {

    public static String chr(int val) {
        return "" + (char) val;
    }

    public static String ansi_char(int val) {
        return chr(val);
    }

    public static char ord(String val) {
        return val.charAt(0);
    }

    public static double real(String val) {
        return Double.parseDouble(val);
    }

    public static String string(Constant c) {
        return (c.isString) ? c.sVal : "" + c.dVal;
    }

    public static String string_format(double val, int t, int d) {
        StringBuffer form = new StringBuffer();
        for (int i = 0; i < t; i++) {
            form.append("0");
        }

        if (d > 0) {
            form.append(".");
            for (int i = 0; i < d; i++) {
                form.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(form.toString());
        return df.format(val, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public static int string_length(String s) {
        return s.length();
    }

    public static int string_byte_length(String s) {
        return s.length();
    }

    public static int string_pos(String substr, String str) {
        return str.indexOf(str) + 1;
    }

    public static String string_copy(String str, int index, int count) {
        return str.substring(index - 1, count);
    }

    public static String string_char_at(String str, int index) {
        return str.substring(index - 1, 1);
    }

    public static int string_byte_at(String str, int index) {
        return str.charAt(index-1);
    }
    /*
     string_delete(str,index,count) 
     string_insert(substr,str,index)
     string_replace(str,substr,newstr) 
     string_replace_all(str,substr,newstr)
     string_count(substr,str)
     string_lower(str)
     string_upper(str)
     string_repeat(str,count)
     string_letters(str) 
     string_digits(str)
     string_lettersdigits(str) 

     clipboard_has_text()
     clipboard_get_text()
     clipboard_set_text(str)
     * */
}
