package org.gcreator.runero.event;

import java.util.ArrayList;

import org.lateralgm.resources.library.RLibAction;

public class Action {

    public static final int ACT_NORMAL = 0;
    public static final int ACT_BEGIN = 1;
    public static final int ACT_END = 2;
    public static final int ACT_ELSE = 3;
    public static final int ACT_EXIT = 4;
    public static final int ACT_REPEAT = 5;
    public static final int ACT_VARIABLE = 6;
    public static final int ACT_CODE = 7;
    public static final int ACT_PLACEHOLDER = 8;
    public static final int ACT_SEPARATOR = 9;
    public static final int ACT_LABEL = 10;

    public static final int EXEC_NONE = 0;
    public static final int EXEC_FUNCTION = 1;
    public static final int EXEC_CODE = 2;

    public boolean relative = false;
    public boolean not = false;
    public int appliesTo;

    public final RLibAction lib;
    public ArrayList<Argument> arguments;
    
    public Action(RLibAction lib) {
        this.lib = lib;
        arguments = new ArrayList<Argument>();
    }
    
}
