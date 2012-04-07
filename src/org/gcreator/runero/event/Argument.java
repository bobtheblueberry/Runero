/*
 * Copyright (C) 2007, 2008 IsmAvatar <IsmAvatar@gmail.com>
 * Copyright (C) 2006, 2007 Clam <clamisgood@gmail.com>
 * Copyright (C) 2007, 2008 Quadduc <quadduc@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.gcreator.runero.event;

import java.awt.Color;

import org.gcreator.runero.gml.exec.VariableRef;
import org.gcreator.runero.res.Code;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFont;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GamePath;
import org.gcreator.runero.res.GameResource;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameScript;
import org.gcreator.runero.res.GameSound;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameTimeline;

public class Argument {

    public static final int ARG_EXPRESSION = 0;
    public static final int ARG_STRING = 1;
    public static final int ARG_BOTH = 2;
    public static final int ARG_BOOLEAN = 3;
    public static final int ARG_MENU = 4;
    public static final int ARG_COLOR = 13;
    @Deprecated
    public static final int ARG_FONTSTRING = 15;
    public static final int ARG_SPRITE = 5;
    public static final int ARG_SOUND = 6;
    public static final int ARG_BACKGROUND = 7;
    public static final int ARG_PATH = 8;
    public static final int ARG_SCRIPT = 9;
    public static final int ARG_GMOBJECT = 10;
    public static final int ARG_ROOM = 11;
    public static final int ARG_FONT = 12;
    public static final int ARG_TIMELINE = 14;

    public final int kind;
    public String val;
    public Code code;
    
    // Values parsed from the string
    public boolean boolVal;
    public Color colorVal;
    public int menuVal = -1;
    public boolean bothIsExpr; // is expression for 'both' types
    public org.gcreator.runero.gml.exec.ExprArgument exprVal;
    public int resVal = -1;
    public VariableRef variableVal;// for set variable action #611

    public Argument(int kind) {
        this.kind = kind;
        val = null;
        code = null;
    }

    public static Class<? extends GameResource> getResourceKind(int argumentKind) {
        switch (argumentKind) {
        case ARG_SPRITE:
            return GameSprite.class;
        case ARG_SOUND:
            return GameSound.class;
        case ARG_BACKGROUND:
            return GameBackground.class;
        case ARG_PATH:
            return GamePath.class;
        case ARG_SCRIPT:
            return GameScript.class;
        case ARG_GMOBJECT:
            return GameObject.class;
        case ARG_ROOM:
            return GameRoom.class;
        case ARG_FONT:
            return GameFont.class;
        case ARG_TIMELINE:
            return GameTimeline.class;
        default:
            return null;
        }
    }

}
