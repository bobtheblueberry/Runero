package org.gcreator.runero.script;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GMLParser {

    public static double getDouble(String s) {
        Pattern regex = Pattern.compile("random\\(([0-9]+(\\.[0-9]+)?)\\)");
        Matcher match = regex.matcher(s);
        if (match.find()) {
            // Random number
            System.out.println("GMLParser: random number " + s);
            double d;
            if (match.groupCount() > 1) {
                d = Double.parseDouble(match.group(1) + match.group(2));
            } else {
                d = Integer.parseInt(match.group(1));
            }
            return Math.random() * d;
        } else {
            regex = Pattern.compile("[0-9]+(\\.[0-9]+)?");
            match = regex.matcher(s);
            if (match.matches()) {
                return Double.parseDouble(s);
            }
        }
        System.out.println("Invalid double " + s);
        
        return 0;
    }
}
