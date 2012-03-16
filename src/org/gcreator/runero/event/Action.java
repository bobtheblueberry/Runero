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
    public static final int ACT_PLACEHOLDER = 8; // useless
    public static final int ACT_SEPARATOR = 9; // useless
    public static final int ACT_LABEL = 10; // useless

    public static final int EXEC_NONE = 0;
    public static final int EXEC_FUNCTION = 1;
    public static final int EXEC_CODE = 2;

    public boolean relative = false;
    public boolean not = false;
    public int appliesTo;

    public final RLibAction lib;
    public ArrayList<Argument> arguments;
    public BlockAction ifAction; // used for Question actions
    public BlockAction elseAction;
    public BlockAction repeatAction; // used for Repeat actions

    public Action(RLibAction lib) {
        this.lib = lib;
        arguments = new ArrayList<Argument>();
    }

    public static class BlockAction {
        public int start;
        public int end;
        public int actionEnd;
        public boolean isBlock; // used to tell if it has Begin <  > End blocks
        public boolean isFake; // shouldn't be, but it can happen
                               // this happens when the question is the
                               // last action in the event, or there is
                               // an empty block after the question
    }
}
