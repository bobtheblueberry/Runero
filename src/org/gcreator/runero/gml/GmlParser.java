package org.gcreator.runero.gml;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.ObjectGroup;
import org.gcreator.runero.res.GameObject;

public class GmlParser {

    public static void setVariable(String name, String value, boolean relative, Instance instance, Instance other) {

        // first check to see if it is a defined variable
        // then check to see if its a constant * variable

        // name could be like other.x
        
    }

    public static Variable getVariable(String name, Instance instance, Instance other) {

        // first check to see if it is a defined variable
        // then check to see if its a constant * variable

        // name could be like other.x
        return null;
    }

    /**
     * Evaluates an expression
     * 
     * @param instance the instance
     * @param s the expression
     * @param other the 'other' if this is a collision event
     * @return
     */
    // TODO: Implement this!
    public static double getExpression(String s, Instance instance, Instance other) {
      
        // object_specialmoon.x/y
        /*Pattern regex = Pattern.compile("([A-Za-z0-9_]+)\\.([_a-zA-Z0-9])$"); // Doesnt work for 1.8 etc.
        Matcher match = regex.matcher(s);
        if (match.find()) {
            String obj = match.group(1);
            int id = -1;
            for (GameObject g : RuneroGame.game.objects) {
                if (g.getName().equals(obj)) {
                    id = g.getId();
                }
            }
            if (id >= 0) {
                if (RuneroGame.room.hasObjectGroup(id)) {
                    // use the first instance like crappy game maker does
                    ObjectGroup g = RuneroGame.room.getObjectGroup(id);
                    if (g.instances.size() == 0)
                        System.out.println("There are no " + obj + " left in this room!");
                    else
                        return getDoubleVariable(g.instances.get(0), match.group(2));
                } else {
                    System.out.println("Room has no objects with id " + id + " (" + obj + ")");
                }
            } else {
                System.out.println("Cannot find object " + obj + "  (" + s + ")" + " from instance "
                        + instance.obj.getName());
            }
        } else*/
            return getDouble(s);
        //return 0;
    }

    public static double getDouble(String s) {
        Pattern regex = Pattern.compile("random\\((\\-?[0-9]+(\\.[0-9]+)?)\\)");
        Matcher match = regex.matcher(s);
        if (match.find()) {
            // Random number
            // System.out.println("GMLParser: random number " + s);
            double d;
            if (match.groupCount() > 2) {
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
    //    System.out.println("Invalid double " + s);

        return 0;
    }

    // What a hack lol
    public static double getDoubleVariable(Instance i, String varname) {
        if (varname.equals("x"))
            return i.x;
        if (varname.equals("y"))
            return i.y;
        System.out.println("Unknown var " + varname);
        return 0;
    }

    // Thank you LateralGM (IsmAvatar, Clam, Quadduc)
    public static Color convertGmColor(int col) {
        return new Color(col & 0xFF, (col & 0xFF00) >> 8, (col & 0xFF0000) >> 16);
    }

    public static int getGmColor(Color col) {
        return col.getRed() | col.getGreen() << 8 | col.getBlue() << 16;
    }
}
