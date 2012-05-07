/*
    This program is a runner for Game Maker style games

    Copyright (c) 2012 Serge Humphrey<bobtheblueberry@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcreator.runero.gml.lib;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Action;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.gfx.Texture;
import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.gml.GmlParser;
import org.gcreator.runero.gml.VariableManager;
import org.gcreator.runero.gml.VariableVal;
import org.gcreator.runero.gml.exec.VariableRef;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFont;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameSprite;
import org.lwjgl.input.Mouse;

/**
 * Implements all Game Maker library actions
 * 
 * @author serge
 * 
 */
public class ActionLibrary {
    // move
    public static final int ACTION_MOVE = 101;
    public static final int SET_MOTION = 102;
    public static final int MOVE_POINT = 105;
    public static final int SET_HSPEED = 103;
    public static final int SET_VSPEED = 104;
    public static final int SET_GRAVITY = 107;
    public static final int REVERSE_XDIR = 113;
    public static final int REVERSE_YDIR = 114;
    public static final int SET_FRICTION = 108;
    public static final int MOVE_TO = 109;
    public static final int MOVE_START = 110;
    public static final int MOVE_RANDOM = 111;
    public static final int SNAP = 117;
    public static final int WRAP = 112;
    public static final int MOVE_CONTACT = 116;
    public static final int BOUNCE = 115;
    public static final int PATH = 119;
    public static final int PATH_END = 124;
    public static final int PATH_POSITION = 122;
    public static final int PATH_SPEED = 123;
    public static final int STEP_LINEAR = 120;
    public static final int STEP_POTENTIAL = 121;
    // main1
    public static final int CREATE_OBJECT = 201;
    public static final int CREATE_OBJECT_MOTION = 206;
    public static final int CREATE_OBJECT_RANDOM = 207;
    public static final int CHANGE_OBJECT = 202;
    public static final int KILL_OBJECT = 203;
    public static final int KILL_POSITION = 204;
    public static final int SET_SPRITE = 541;
    public static final int TRANSFORM_SPRITE = 542;
    public static final int COLOR_SPRITE = 543;
    public static final int SET_SPRITE_OLD = 205;                // DEPRECATED
    public static final int BEGIN_SOUND = 211;
    public static final int END_SOUND = 212;
    public static final int IF_SOUND = 213;
    public static final int PREVIOUS_ROOM = 221;
    public static final int NEXT_ROOM = 222;
    public static final int CURRENT_ROOM = 223;
    public static final int ANOTHER_ROOM = 224;
    public static final int IF_PREVIOUS_ROOM = 225;
    public static final int IF_NEXT_ROOM = 226;
    // main2
    public static final int SET_ALARM = 301;
    public static final int SLEEP = 302;
    public static final int SET_TIMELINE = 303;
    public static final int POSITION_TIMELINE = 304;
    public static final int MESSAGE = 321;
    public static final int SHOW_INFO = 322;
    public static final int SHOW_VIDEO = 323;
    // Game Maker 8 Splash functions
    public static final int SPLASH_TEXT = 324;
    public static final int SPLASH_IMAGE = 325;
    public static final int SPLASH_WEB = 326;
    public static final int SPLASH_VIDEO = 327;
    public static final int SPLASH_SETTINGS = 328;

    public static final int RESTART_GAME = 331;
    public static final int END_GAME = 332;
    public static final int SAVE_GAME = 333;
    public static final int LOAD_GAME = 334;
    public static final int REPLACE_SPRITE = 803;
    public static final int REPLACE_SOUND = 804;
    public static final int REPLACE_BACKGROUND = 805;
    // control
    public static final int IF_EMPTY = 401;
    public static final int IF_COLLISION = 402;
    public static final int IF_OBJECT = 403;
    public static final int IF_NUMBER = 404;
    public static final int IF_DICE = 405;
    public static final int IF_QUESTION = 407;
    public static final int IF = 408;
    public static final int IF_MOUSE = 409;
    public static final int IF_ALIGNED = 410;
    public static final int START_BLOCK = 422;                // not used
    public static final int ELSE = 421;                // not used
    public static final int EXIT = 425;                // not used
    public static final int END_BLOCK = 424;                // useless
    public static final int REPEAT = 423;                // very useless
    public static final int INHERITED = 604;
    public static final int CODE = 603;                // not actually used
    public static final int EXECUTE_SCRIPT = 601;
    public static final int COMMENT = 605;
    public static final int VARIABLE = 611;
    public static final int IF_VARIABLE = 612;
    public static final int DRAW_VARIABLE = 613;
    // score
    public static final int SET_SCORE = 701;
    public static final int IF_SCORE = 702;
    public static final int DRAW_SCORE = 703;
    public static final int HIGHSCORE_SHOW = 709;
    public static final int HIGHSCORE_CLEAR = 707;
    public static final int SET_LIFE = 711;
    public static final int IF_LIFE = 712;
    public static final int DRAW_LIFE = 713;
    public static final int DRAW_LIFE_IMAGES = 714;
    public static final int SET_HEALTH = 721;
    public static final int IF_HEALTH = 722;
    public static final int DRAW_HEALTH = 723;
    public static final int SET_CAPTION = 731;
    // extra i.e. never going to be implemented
    public static final int PART_SYST_CREATE = 820;
    public static final int PART_SYST_DESTROY = 821;
    public static final int PART_SYST_CLEAR = 822;
    public static final int PART_TYPE_CREATE_OLD = 825;                // DEPRECATED
    public static final int PART_TYPE_CREATE = 823;
    public static final int PART_TYPE_COLOR = 824;
    public static final int PART_TYPE_LIFE = 826;
    public static final int PART_TYPE_SPEED = 827;
    public static final int PART_TYPE_GRAVITY = 828;
    public static final int PART_TYPE_SECONDARY = 829;
    public static final int PART_EMIT_CREATE = 831;
    public static final int PART_EMIT_DESTROY = 832;
    public static final int PART_EMIT_BURST = 833;
    public static final int PART_EMIT_STREAM = 834;
    public static final int CD_PLAY = 808;
    public static final int CD_STOP = 809;
    public static final int CD_PAUSE = 810;
    public static final int CD_RESUME = 811;
    public static final int CD_IF_EXISTS = 812;
    public static final int CD_IF_PLAYING = 813;
    public static final int SET_MOUSE = 801;
    public static final int OPEN_WEBPAGE = 807;
    // draw
    public static final int DRAW_SPRITE = 501;
    public static final int DRAW_BACKGROUND = 502;
    public static final int DRAW_TEXT = 514;
    public static final int DRAW_TEXT_SCALED = 519;
    public static final int DRAW_RECTANGLE = 511;
    public static final int DRAW_GRADIENT_HOR = 516;
    public static final int DRAW_GRADIENT_VERT = 517;
    public static final int DRAW_ELLIPSE = 512;
    public static final int DRAW_ELLIPSE_GRADIENT = 518;
    public static final int DRAW_LINE = 513;
    public static final int DRAW_ARROW = 515;
    public static final int SET_COLOR = 524;
    public static final int SET_FONT = 526;
    public static final int FULLSCREEN = 531;
    public static final int TAKE_SNAPSHOT = 802;
    public static final int EFFECT = 532;

    private static GraphicsLibrary g = GraphicsLibrary.gfx;

    public static void executeAction(Action act, Instance instance) {
        executeAction(act, instance, null);
    }

    /**
     * ActionLibrary doesn't deal with silly function names, etc. It just looks up the action ID to see what to do.
     * 
     * @param instance
     * @param act
     * @return
     */
    public static void executeAction(Action act, Instance instance, Instance other) {
        if (act.lib.question) {
            System.err.println("Error! Game Engine used wrong function to execute action");
            return;
        }
        if (act.lib.execType == Action.EXEC_NONE) {
            if (act.lib.id != COMMENT) {
                System.out.println("?? Nothing action != Comment ?? " + act.lib.name);
            }
            return;
        }

        switch (act.lib.id) {
        // move
            case ACTION_MOVE:
                action_move(act, instance, other);
                return;
            case SET_MOTION:
                set_motion(act, instance, other);
                return;
            case MOVE_POINT:
                move_point(act, instance, other);
                return;
            case SET_HSPEED:
                set_hspeed(act, instance, other);
                return;
            case SET_VSPEED:
                set_vspeed(act, instance, other);
                return;
            case SET_GRAVITY:
                set_gravity(act, instance, other);
                return;
            case REVERSE_XDIR:
                reverse_xdir(act, instance, other);
                return;
            case REVERSE_YDIR:
                reverse_ydir(act, instance, other);
                return;
            case SET_FRICTION:
                set_friction(act, instance, other);
                return;
            case MOVE_TO:
                move_to(act, instance, other);
                return;
            case MOVE_START:
                move_start(act, instance, other);
                return;
            case MOVE_RANDOM:
                move_random(act, instance, other);
                return;
            case SNAP:
                snap(act, instance, other);
                return;
            case WRAP:
                wrap(act, instance, other);
                return;
            case MOVE_CONTACT:
                // TODO: This, and collision system
                // TODO: THIS
                return;
            case BOUNCE:
                // TODO: This, and collision system
                // TODO: THIS
                return;
            case PATH:
                // TODO: Paths
                // TODO: THIS
                return;
            case PATH_END:

                // TODO: THIS
                return;
            case PATH_POSITION:

                // TODO: THIS
                return;
            case PATH_SPEED:

                // TODO: THIS
                return;
            case STEP_LINEAR:
                // TODO: Stepping
                // TODO: THIS
                return;
            case STEP_POTENTIAL:

                // TODO: THIS
                return;
                // main1
            case CREATE_OBJECT:
                create_object(act, instance, other);
                return;
            case CREATE_OBJECT_MOTION:
                create_object_motion(act, instance, other);
                return;
            case CREATE_OBJECT_RANDOM:
                create_object_random(act, instance, other);
                return;
            case CHANGE_OBJECT:
                change_object(act, instance, other);
                return;
            case KILL_OBJECT:
                kill_object(act, instance, other);
                return;
            case KILL_POSITION: // TODO: Implement this!
                kill_position(act, instance, other);
                return;
            case SET_SPRITE:
                set_sprite(act, instance, other);
                return;
            case TRANSFORM_SPRITE:
                transform_sprite(act, instance, other);
                return;
            case COLOR_SPRITE:
                color_sprite(act, instance, other);
                return;
            case SET_SPRITE_OLD: // Deprecated
                set_sprite_old(act, instance, other);
                return;
            case BEGIN_SOUND:

                // TODO: THIS
                return;
            case END_SOUND:

                // TODO: THIS
                return;
            case PREVIOUS_ROOM:
                previous_room(act, instance, other);
                return;
            case NEXT_ROOM:
                next_room(act, instance, other);
                return;
            case CURRENT_ROOM:
                current_room(act, instance, other);
                return;
            case ANOTHER_ROOM:
                another_room(act, instance, other);
                return;
                // main2
            case SET_ALARM:
                set_alarm(act, instance, other);
                return;
            case SLEEP:
                sleep(act, instance, other);
                return;
            case SET_TIMELINE:

                // TODO: THIS
                return;
            case POSITION_TIMELINE:

                // TODO: THIS
                return;
            case MESSAGE:
                show_message(act, instance, other);
                return;
            case SHOW_INFO:
                show_info(act, instance, other);
                return;
            case SHOW_VIDEO:
                show_video(act, instance, other);
                return;
            case SPLASH_TEXT:
                splash_text(act, instance, other);
                return;
            case SPLASH_IMAGE:
                splash_image(act, instance, other);
                return;
            case SPLASH_WEB:
                splash_web(act, instance, other);
                return;
            case SPLASH_VIDEO:
                splash_video(act, instance, other);
                return;
            case SPLASH_SETTINGS:
                splash_settings(act, instance, other);
                return;
            case RESTART_GAME:
                restart_game(act, instance, other);
                return;
            case END_GAME:
                end_game(act, instance, other);
                return;
            case SAVE_GAME:
                save_game(act, instance, other);
                return;
            case LOAD_GAME:
                load_game(act, instance, other);
                return;
            case REPLACE_SPRITE:
                replace_sprite(act, instance, other);
                return;
            case REPLACE_SOUND:
                replace_sound(act, instance, other);
                return;
            case REPLACE_BACKGROUND:
                replace_background(act, instance, other);
                return;
                // control
            case INHERITED:
                // This is actually handled by the GmlInterpreter class.
                return;
            case CODE:
                execute_code(act, instance, other);
                return;
            case EXECUTE_SCRIPT:
                execute_script(act, instance, other);
                return;
            case VARIABLE:
                variable(act, instance, other);
                return;
            case DRAW_VARIABLE:
                draw_variable(act, instance, other);
                return;
                // score
            case SET_SCORE:
                set_score(act, instance, other);
                return;
            case DRAW_SCORE:
                draw_score(act, instance, other);
                return;
            case HIGHSCORE_SHOW:
                highscore_show(act, instance, other);
                return;
            case HIGHSCORE_CLEAR:
                highscore_clear(act, instance, other);
                return;
            case SET_LIFE:
                set_life(act, instance, other);
                return;
            case DRAW_LIFE:
                draw_life(act, instance, other);
                return;
            case DRAW_LIFE_IMAGES:
                draw_life_images(act, instance, other);
                return;
            case SET_HEALTH:
                set_health(act, instance, other);
                return;
            case DRAW_HEALTH:
                draw_health(act, instance, other);
                return;
            case SET_CAPTION:
                set_caption(act, instance, other);
                return;
                // extra i.e. never going to be implemented
            case PART_SYST_CREATE:

                // TODO: THIS
                return;
            case PART_SYST_DESTROY:

                // TODO: THIS
                return;
            case PART_SYST_CLEAR:

                // TODO: THIS
                return;
            case PART_TYPE_CREATE_OLD:

                // TODO: THIS
                return;
            case PART_TYPE_CREATE:

                // TODO: THIS
                return;
            case PART_TYPE_COLOR:

                // TODO: THIS
                return;
            case PART_TYPE_LIFE:

                // TODO: THIS
                return;
            case PART_TYPE_SPEED:

                // TODO: THIS
                return;
            case PART_TYPE_GRAVITY:

                // TODO: THIS
                return;
            case PART_TYPE_SECONDARY:

                // TODO: THIS
                return;
            case PART_EMIT_CREATE:

                // TODO: THIS
                return;
            case PART_EMIT_DESTROY:

                // TODO: THIS
                return;
            case PART_EMIT_BURST:

                // TODO: THIS
                return;
            case PART_EMIT_STREAM:

                // TODO: THIS
                return;
            case CD_PLAY:

                // TODO: THIS
                return;
            case CD_STOP:

                // TODO: THIS
                return;
            case CD_PAUSE:

                // TODO: THIS
                return;
            case CD_RESUME:

                // TODO: THIS
                return;
            case SET_MOUSE:
                // TODO: set mouse
                // TODO: THIS
                return;
            case OPEN_WEBPAGE:
                open_webpage(act, instance, other);
                return;
                // draw
            case DRAW_SPRITE:
                draw_sprite(act, instance, other);
                return;
            case DRAW_BACKGROUND:
                draw_background(act, instance, other);
                return;
            case DRAW_TEXT:
                draw_text(act, instance, other);
                return;
            case DRAW_TEXT_SCALED:
                draw_text_scaled(act, instance, other);
                return;
            case DRAW_RECTANGLE:
                draw_rectangle(act, instance, other);
                return;
            case DRAW_GRADIENT_HOR:
                draw_gradient_hor(act, instance, other);
                return;
            case DRAW_GRADIENT_VERT:
                draw_gradient_vert(act, instance, other);
                return;
            case DRAW_ELLIPSE:
                draw_ellipse(act, instance, other);
                return;
            case DRAW_ELLIPSE_GRADIENT:
                draw_ellipse_gradient(act, instance, other);
                return;
            case DRAW_LINE:
                draw_line(act, instance, other);
                return;
            case DRAW_ARROW:
                draw_arrow(act, instance, other);
                return;
            case SET_COLOR:
                set_color(act, instance, other);
                return;
            case SET_FONT:
                set_font(act, instance, other);
                return;
            case FULLSCREEN:
                fullscreen(act, instance, other);
                return;
            case TAKE_SNAPSHOT:
                take_snapshot(act, instance, other);
                return;
            case EFFECT:
                effect(act, instance, other);
                return;
        }
    }

    public static boolean executeQuestion(Action act, Instance instance, Instance other) {
        boolean b = executeQuestion_(act, instance, other);
        if (act.not)
            b = !b;
        return b;
    }

    // There are 18 question actions as of GM7
    private static boolean executeQuestion_(Action act, Instance instance, Instance other) {
        switch (act.lib.id) {
            case IF_EMPTY:
                return if_empty(act, instance, other);
            case IF_COLLISION:
                return if_collision(act, instance, other);
            case IF_OBJECT:
                return if_object(act, instance, other);
            case IF_NUMBER:
                return if_number(act, instance, other);
            case IF_DICE:
                return if_dice(act, instance, other);
            case IF_QUESTION:
                return if_question(act, instance, other);
            case IF:
                return if_expression(act, instance, other);
            case IF_MOUSE:
                return if_mouse(act, instance, other);
            case IF_ALIGNED:
                return false;
            case IF_SOUND:
                return if_sound(act, instance, other);
            case IF_SCORE:
                return if_score(act, instance, other);
            case IF_VARIABLE:
                return if_variable(act, instance, other);
            case IF_LIFE:
                return if_life(act, instance, other);
            case IF_HEALTH:
                return if_health(act, instance, other);
            case IF_PREVIOUS_ROOM:
                return if_previous_room(act, instance, other);
            case IF_NEXT_ROOM:
                return if_next_room(act, instance, other);
            case CD_IF_EXISTS:
                return false;
            case CD_IF_PLAYING:
                return false;
        }
        return false;
    }

    /**
     * 
     * @param var1
     * @param var2
     * @param op 0 = equal, 1 = smaller than, 2 = greater than
     * @return
     */
    private static boolean compare(VariableVal var1, VariableVal var2, int op) {
        if (op == 0) {
            return var1.equals(var2);
        } else {
            if (var1.isReal && var2.isReal) {
                if (op == 1)
                    return var1.realVal < var2.realVal;
                else
                    return var1.realVal > var2.realVal;
            }
            // Cannot compare string values like that
        }
        return false;
    }

    private static boolean if_variable(Action act, Instance instance, Instance other) {
        // variable,value,comparator menu
        // equal to|smaller than|larger than
        VariableVal var = GmlParser.solve(act.arguments.get(0).exprVal, instance, other);
        VariableVal val = GmlParser.solve(act.arguments.get(1).exprVal, instance, other);
        int op = act.arguments.get(2).menuVal;
        return compare(var, val, op);
    }

    private static boolean if_life(Action act, Instance instance, Instance other) {
        // value, operation {equal to|smaller than|larger than}
        VariableVal lives = new VariableVal(RuneroGame.game.lives);
        VariableVal val = GmlParser.solve(act.arguments.get(0).exprVal, instance, other);
        int op = act.arguments.get(1).menuVal;
        return compare(lives, val, op);
    }

    private static boolean if_health(Action act, Instance instance, Instance other) {
        VariableVal health = new VariableVal(RuneroGame.game.health);
        VariableVal val = GmlParser.solve(act.arguments.get(0).exprVal, instance, other);
        int op = act.arguments.get(1).menuVal;
        return compare(health, val, op);
    }

    private static boolean if_score(Action act, Instance instance, Instance other) {
        VariableVal score = new VariableVal(RuneroGame.game.score);
        VariableVal val = GmlParser.solve(act.arguments.get(0).exprVal, instance, other);
        int op = act.arguments.get(1).menuVal;
        return compare(score, val, op);
    }

    private static boolean if_previous_room(Action act, Instance instance, Instance other) {
        return RuneroGame.game.room_index > 0; // should work
    }

    private static boolean if_next_room(Action act, Instance instance, Instance other) {
        return RuneroGame.game.room_index < RuneroGame.game.rooms.size() - 1;
    }

    private static boolean if_sound(Action act, Instance instance, Instance other) {
        // sound resource
        // TODO This, and sound system
        return false;
    }

    private static boolean if_mouse(Action act, Instance instance, Instance other) {
        // no|left|right|middle
        int index = act.arguments.get(0).menuVal;
        if (index == 0) {
            return !Mouse.isButtonDown(0) && Mouse.isButtonDown(1) && Mouse.isButtonDown(2);
        } else
            return Mouse.isButtonDown(index - 1);
    }

    private static boolean if_dice(Action act, Instance instance, Instance other) {
        // sides
        double sides = GmlParser.getExpression(act.arguments.get(0).exprVal, instance, other);
        return Math.random() * sides < 1;
    }

    private static boolean if_question(Action act, Instance instance, Instance other) {
        String msg = GmlParser.getExpressionString(act.arguments.get(0), instance, other);
        // TODO: real function, not this hack
        int opt = JOptionPane.showConfirmDialog(null, msg, "Confirm", JOptionPane.YES_NO_OPTION);
        return opt == JOptionPane.YES_OPTION;
    }

    private static boolean if_number(Action act, Instance instance, Instance other) {
        // object, number, operation{Equal to|Smaller than|Larger than}
        int object = act.arguments.get(2).resVal;
        double number = GmlParser.getExpression(act.arguments.get(1).exprVal, instance, other);
        int op = act.arguments.get(2).menuVal;

        if (!RuneroGame.room.hasObjectGroup(object))
            return false;
        int objNum = RuneroGame.room.getObjectGroup(object).instances.size();
        if (op == 0)
            return objNum == number;
        else if (op == 1)
            return objNum < number;
        else
            // 2
            return objNum > number;
    }

    private static boolean if_object(Action act, Instance instance, Instance other) {
        // TODO this, and collision checking system
        return false;
    }

    private static boolean if_collision(Action act, Instance instance, Instance other) {
        // TODO this, and collision checking system
        return false;
    }

    private static boolean if_empty(Action act, Instance instance, Instance other) {
        // TODO this, and collision checking system
        return false;
    }

    private static boolean if_expression(Action act, Instance instance, Instance other) {
        Constant val = act.arguments.get(0).exprVal.solve(instance, other);
        if (val.isString)
            return false;
        return val.dVal > 0.5;// JUST LIKE GAME MAKER DOES!
    }

    private static void action_move(Action a, Instance instance, Instance other) {
        // Move Fixed..
        // Arrows.. yay
        double speed = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        String arg0 = a.arguments.get(0).val;
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            if (arg0.charAt(i) != '1')
                continue;
            vals.add(i);
        }
        if (vals.size() == 0) {
            return;
        }
        int x = (int) (Math.random() * vals.size());
        int r = vals.get(x);
        if (r == 4) { // stop (middle button)
            instance.setSpeed(0);
            return;
        }
        int d = 0;
        if (r == 0)
            d = 225;
        else if (r == 1)
            d = 270;
        else if (r == 2)
            d = 315;
        else if (r == 3)
            d = 180;
        else if (r == 5)
            d = 0;
        else if (r == 6)
            d = 135;
        else if (r == 7)
            d = 90;
        else if (r == 8)
            d = 45;
        if (a.relative)
            speed += instance.getSpeed();
        instance.motion_set(d, speed);
    }

    private static void set_motion(Action a, Instance instance, Instance other) {
        double direction = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        if (a.relative)
            instance.motion_add(direction, speed);
        else
            instance.motion_set(direction, speed);
    }

    private static void move_point(Action a, Instance instance, Instance other) {
        // x,y,speed
        double x = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double dir;
        if (a.relative) {
            dir = MathLibrary.point_direction(instance.x, instance.y, instance.x + x, instance.y + y);
        } else {
            dir = MathLibrary.point_direction(instance.x, instance.y, x, y);
        }
        instance.motion_set(dir, speed);
    }

    private static void set_hspeed(Action a, Instance instance, Instance other) {
        double hspeed = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        if (a.relative)
            instance.setHspeed(instance.getHspeed() + hspeed);
        else
            instance.setHspeed(hspeed);
    }

    private static void set_vspeed(Action a, Instance instance, Instance other) {
        double vspeed = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        if (a.relative)
            instance.setVspeed(instance.getHspeed() + vspeed);
        else
            instance.setVspeed(vspeed);
    }

    private static void set_gravity(Action a, Instance instance, Instance other) {
        double direction = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double gravity = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        instance.gravity_direction = direction;
        if (a.relative)
            instance.gravity += gravity;
        else
            instance.gravity = gravity;
    }

    private static void reverse_xdir(Action a, Instance instance, Instance other) {
        instance.setHspeed(-instance.getHspeed());
    }

    private static void reverse_ydir(Action a, Instance instance, Instance other) {
        instance.setVspeed(-instance.getVspeed());
    }

    private static void set_friction(Action a, Instance instance, Instance other) {
        double friction = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        instance.friction = friction;
    }

    private static void move_to(Action a, Instance instance, Instance other) {
        double x = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        if (a.relative) {
            instance.x += x;
            instance.y += y;
        } else {
            instance.x = x;
            instance.y = y;
        }
    }

    private static void move_start(Action a, Instance instance, Instance other) {
        instance.x = instance.xstart;
        instance.y = instance.ystart;
    }

    private static void move_random(Action a, Instance instance, Instance other) {
        // TODO: Solid object checking

        double xsnap = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double ysnap = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);

        double rx = Math.random() * RuneroGame.room.width;
        double ry = Math.random() * RuneroGame.room.height;
        if (xsnap > 0)
            rx = ((int) (rx / xsnap)) * xsnap;
        if (ysnap > 0)
            ry = ((int) (ry / ysnap)) * ysnap;
        instance.x = rx;
        instance.y = ry;
    }

    private static void snap(Action a, Instance instance, Instance other) {
        double h = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double v = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double newX = Math.round(instance.x / h);
        double newY = Math.round(instance.y / v);
        instance.x = newX * h;
        instance.y = newY * v;
    }

    private static void wrap(Action a, Instance instance, Instance other) {
        // horizontal|vertical|in both directions
        int dir = a.arguments.get(0).menuVal;
        GameSprite s = instance.getSprite();
        int x = 0, y = 0;
        if (s != null) {
            x = s.x;
            y = s.y;
        }
        if (dir == 0 || dir == 2) {
            double newx = instance.x;
            int w = RuneroGame.room.width;
            // TODO: take the sprite into account
            if (newx < -x) {
                newx = w + x;
            } else if (newx > w) {
                newx = -x;
            }
            instance.x = newx;
        }
        if (dir == 1 || dir == 2) {
            double newy = instance.y;
            int h = RuneroGame.room.height;
            if (newy < -y) {
                newy = h + y;
            } else if (newy > h) {
                newy = -y;
            }
            instance.y = newy;
        }
    }

    /*TODO:
    MOVE_CONTACT
    BOUNCE
    PATH
    PATH_END
    PATH_POSITION
    PATH_SPEED
    STEP_LINEAR
    STEP_POTENTIAL*/

    // main1
    private static void create_object(Action a, Instance instance, Instance other) {
        int id = a.arguments.get(0).resVal;
        if (id < 0) {
            System.out.println("cannot create object with id " + id);
            return;
        }
        GameObject o = RuneroGame.game.getObject(id);
        if (o == null) {
            System.out.println("Unknown object " + id);
            return;
        }
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y);
    }

    private static void create_object_motion(Action a, Instance instance, Instance other) {
        int id = a.arguments.get(0).resVal;
        if (id < 0) {
            System.out.println("cannot create object with id " + id);
            return;
        }
        GameObject o = RuneroGame.game.getObject(id);
        if (o == null) {
            System.out.println("Unknown object " + id);
            return;
        }
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        double dir = GmlParser.getExpression(a.arguments.get(4).exprVal, instance, other);

        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y, dir, speed);
    }

    private static void create_object_random(Action a, Instance instance, Instance other) {
        int[] ids = new int[4];
        ids[0] = a.arguments.get(0).resVal;
        ids[1] = a.arguments.get(1).resVal;
        ids[2] = a.arguments.get(2).resVal;
        ids[3] = a.arguments.get(3).resVal;
        ArrayList<GameObject> objs = new ArrayList<GameObject>(4);
        for (int i : ids) {
            if (i < 0)
                continue;
            GameObject o = RuneroGame.game.getObject(i);
            if (o == null) {
                System.out.println("Unknown object " + i);
                continue;
            }
            objs.add(o);
        }
        if (objs.size() == 0) {
            System.out.println("create random failed; no valid objects");
            return;
        }
        int r = (int) (Math.random() * objs.size());
        GameObject o = objs.get(r);
        double x = GmlParser.getExpression(a.arguments.get(4).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(5).exprVal, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y);
    }

    private static void change_object(Action a, Instance instance, Instance other) {
        int id = a.arguments.get(0).resVal;
        int performEvents = Integer.parseInt(a.arguments.get(1).val);
        GameObject g = RuneroGame.game.getObject(id);
        if (g == null) {
            System.out.println("unknown object " + id);
            return;
        }
        RuneroGame.room.changeInstance(instance, g, performEvents == 1);
    }

    private static void kill_object(Action a, Instance instance, Instance other) {
        RuneroGame.room.destoryInstance(instance);
    }

    private static void kill_position(Action a, Instance instance, Instance other) {
        // TODO: THIS, and collision system
    }

    private static void set_sprite(Action a, Instance instance, Instance other) {
        // Use -1 if you do not want to change the current subimage shown.
        int sprite = a.arguments.get(0).resVal;
        double subimg = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        instance.sprite_index = sprite;
        if (subimg >= 0)
            instance.image_index = subimg;
        instance.image_speed = speed;
        // update instance image_number
        GameSprite s = RuneroGame.game.getSprite(sprite);
        if (s == null)
            instance.image_number = 0;
        else
            instance.image_number = s.subImages.size();
    }

    private static void transform_sprite(Action a, Instance instance, Instance other) {
        // xscale, yscale, angle, mirror (menu)
        double xscale = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double yscale = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double angle = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        int mirror = a.arguments.get(3).menuVal;
        // no mirroring|mirror horizontally|flip vertically|mirror and flip
        if (mirror == 1 || mirror == 2) {
            xscale = -xscale;
        }
        if (mirror == 2 || mirror == 2) {
            yscale = -yscale;
        }
        instance.image_xscale = xscale;
        instance.image_yscale = yscale;
        instance.image_angle = angle;
    }

    private static void color_sprite(Action a, Instance instance, Instance other) {
        Color color = a.arguments.get(0).colorVal;
        double alpha = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        instance.image_blend = color;
        instance.image_alpha = alpha;
    }

    // not used anymore
    private static void set_sprite_old(Action a, Instance instance, Instance other) {
        int sprite = a.arguments.get(0).resVal;
        double scale = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        instance.sprite_index = sprite;
        instance.image_xscale = scale;
        instance.image_yscale = scale;
    }

    /*BEGIN_SOUND
    END_SOUND
    */

    private static void another_room(Action act, Instance instance, Instance other) {
        RoomLibrary.room_goto(act.arguments.get(0).resVal, act.arguments.get(1).menuVal);
    }

    private static void current_room(Action act, Instance instance, Instance other) {
        // restart room
        RoomLibrary.room_restart(act.arguments.get(0).menuVal);
    }

    private static void next_room(Action act, Instance instance, Instance other) {
        RoomLibrary.room_goto_next(act.arguments.get(0).menuVal);
    }

    private static void previous_room(Action act, Instance instance, Instance other) {
        RoomLibrary.room_goto_previous(act.arguments.get(0).menuVal);
    }

    // main2
    private static void set_alarm(Action a, Instance instance, Instance other) {
        int steps = (int) Math.round(GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other));
        int number = a.arguments.get(1).menuVal;
        instance.alarm[number] = steps;
    }

    private static void sleep(Action a, Instance instance, Instance other) {
        double seconds = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        boolean redraw = a.arguments.get(0).boolVal;
        if (redraw) {
            // TODO: REDRAW
        }
        try {
            System.out.println("sleep " + seconds);
            Thread.sleep((long) seconds);
        } catch (InterruptedException e) {
            System.out.println("Somebody bitched while game slept.");
        }
    }

    /*TODO:
    SET_TIMELINE
    POSITION_TIMELINE
    */
    private static void show_message(Action a, Instance instance, Instance other) {
        // TODO: real function, not this hack
        // also todo, # newlines, \# escape
        String msg = GmlParser.getExpressionString(a.arguments.get(0), instance, other);
        JOptionPane.showMessageDialog(null, msg);
    }

    private static void show_info(Action a, Instance instance, Instance other) {
        RuneroGame.game.gameInfo.showInfoWindow();
    }

    private static void show_video(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void restart_game(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void end_game(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void save_game(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void load_game(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void replace_sprite(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void replace_sound(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void replace_background(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void execute_code(Action a, Instance instance, Instance other) {
        a.arguments.get(0).code.getCode().execute(instance, other);
    }

    private static void execute_script(Action a, Instance instance, Instance other) {
        // script, arg0,arg1,arg2,arg3,arg4

    }

    // GM8 Splash functions that SUCK
    private static void splash_text(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void splash_image(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void splash_web(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void splash_video(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void splash_settings(Action a, Instance instance, Instance other) {
        // TODO: This
    }

    private static void variable(Action a, Instance instance, Instance other) {
        // variable name, value, relative
        VariableRef r = a.arguments.get(0).variableVal;
        Constant val = a.arguments.get(1).exprVal.solve(instance, other);
        VariableManager.setVariable(r, val, a.relative, instance, other);
    }

    private static void draw_variable(Action a, Instance instance, Instance other) {
        VariableVal var = GmlParser.solve(a.arguments.get(0).exprVal, instance, other);
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        String s = var.isReal ? "" + var.realVal : var.val;
        g.drawString(s, (float) x, (float) y);
    }

    // score
    private static void set_score(Action a, Instance instance, Instance other) {
        double newScore = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        if (a.relative) {
            RuneroGame.game.score += newScore;
        } else {
            RuneroGame.game.score = newScore;
        }
    }

    private static void draw_score(Action a, Instance instance, Instance other) {
        // x, y, caption
        double x = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        String caption = a.arguments.get(2).val;
        g.drawString(caption + RuneroGame.game.score, (float) x, (float) y);
    }

    private static void highscore_show(Action a, Instance instance, Instance other) {

    }

    private static void highscore_clear(Action a, Instance instance, Instance other) {
    }

    private static void set_life(Action a, Instance instance, Instance other) {
        double newLife = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        if (a.relative) {
            RuneroGame.game.lives += newLife;
        } else {
            RuneroGame.game.lives = newLife;
        }
    }

    private static void draw_life(Action a, Instance instance, Instance other) {
        double x = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        String caption = a.arguments.get(2).val;
        g.drawString(caption + RuneroGame.game.lives, (float) x, (float) y);
    }

    private static void draw_life_images(Action a, Instance instance, Instance other) {
        int lives = (int) RuneroGame.game.lives;
        if (lives <= 0) {
            System.out.println("no lives in action draw life images");
            return;
        }

        double x = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        int sprite = a.arguments.get(2).resVal;
        GameSprite s = RuneroGame.game.getSprite(sprite);
        if (s == null) {
            System.out.println("Can't draw life images; unkown sprite index " + sprite);
            return;
        }
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        if (s.subImages.size() == 0) {
            return;
        }
        Texture lifeImg = s.getTexture(0);
        for (int i = 0; i < lives; i++) {
            int off = lifeImg.getImageWidth() * i;
            g.drawTexture(lifeImg, x + off, y);
        }
        // a nice thing would be to draw part of an image if lives is, say 1.5
        // even though Game Maker does not have this feature.
    }

    private static void set_health(Action a, Instance instance, Instance other) {
        // 0 - 100 ... supposedly
        RuneroGame.game.health = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
    }

    private static void draw_health(Action a, Instance instance, Instance other) {
        // x1, y1, x2, y2
        // back color
        // (none|black|gray|silver|white|maroon|green|olive|navy|purple|teal|red|lime|yellow|blue|fuchsia|aqua)
        // bar color (green to red|white to
        // black|black|gray|silver|white|maroon|green|olive|navy|purple|teal|red|lime|yellow|blue|fuchsia|aqua)
        // Fuuuuckkk this
    }

    private static void set_caption(Action a, Instance instance, Instance other) {
        boolean showScore = a.arguments.get(0).menuVal == 1;
        String captionScore = a.arguments.get(1).val;
        boolean showLives = a.arguments.get(2).menuVal == 1;
        String captionLives = a.arguments.get(3).val;
        boolean showHealth = a.arguments.get(4).menuVal == 1;
        String captionHealth = a.arguments.get(5).val;
        RuneroGame.game.show_score = showScore;
        RuneroGame.game.show_lives = showLives;
        RuneroGame.game.show_health = showHealth;
        RuneroGame.game.caption_score = captionScore;
        RuneroGame.game.caption_lives = captionLives;
        RuneroGame.game.caption_health = captionHealth;
    }

    // extra i.e. never going to be implemented
    /*
    PART_SYST_CREATE
    PART_SYST_DESTROY
    PART_SYST_CLEAR
    PART_TYPE_CREATE_OLD
    PART_TYPE_CREATE
    PART_TYPE_COLOR
    PART_TYPE_LIFE
    PART_TYPE_SPEED
    PART_TYPE_GRAVITY
    PART_TYPE_SECONDARY
    PART_EMIT_CREATE
    PART_EMIT_DESTROY
    PART_EMIT_BURST
    PART_EMIT_STREAM
    CD_PLAY
    CD_STOP
    CD_PAUSE
    CD_RESUME
    CD_IF_EXISTS
    CD_IF_PLAYING
    TODO:  SET_MOUSE
    */
    private static void open_webpage(Action a, Instance instance, Instance other) {
        // Note: URL is supposed to be an expression OR a string, an expression when
        // it starts with a single or double quote. This is the same for show_message
        // however, I have tried it in Game Maker and it does not seem to work.
        // BROKEN IN GM7, WORKS IN GM6.1
        String url = GmlParser.getExpressionString(a.arguments.get(0), instance, other);
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            System.out.println("error creating url  + url");
        }
    }

    // draw
    private static void draw_sprite(Action a, Instance instance, Instance other) {
        int sprite = a.arguments.get(0).resVal;
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        GameSprite s = RuneroGame.game.getSprite(sprite);
        if (s == null) {
            System.out.println("Can't draw sprite; unkown sprite index " + sprite);
            return;
        }
        if (s.subImages.size() == 0) {
            System.out.println("Cannot draw sprite with no sub-images");
            return;
        }
        int subImage = Integer.parseInt(a.arguments.get(3).val);
        if (subImage == -1) {
            subImage = (int) Math.round(instance.sprite_index);
        }

        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        Texture img = s.getTexture(subImage % s.subImages.size());
        g.drawTexture(img, x, y);
    }

    private static void draw_background(Action a, Instance instance, Instance other) {
        int background = a.arguments.get(0).resVal;
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        boolean tiled = a.arguments.get(3).boolVal;

        GameBackground bg = RuneroGame.game.getBackground(background);
        if (bg == null) {
            System.out.println("Can't draw unknown background " + background);
            return;
        }
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        g.drawBackground(bg, x, y, tiled, tiled, false);
    }

    private static void draw_text(Action a, Instance instance, Instance other) {
        String text = GmlParser.getExpressionString(a.arguments.get(0), instance, other);
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);

        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        g.drawString(text, (float) x, (float) y);
    }

    private static void draw_text_scaled(Action a, Instance instance, Instance other) {
        String text = GmlParser.getExpressionString(a.arguments.get(0), instance, other);
        double x = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double xscale = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        double yscale = GmlParser.getExpression(a.arguments.get(4).exprVal, instance, other);
        double angle = GmlParser.getExpression(a.arguments.get(5).exprVal, instance, other);

        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        g.drawString(x, y, text, xscale, yscale, angle);
    }

    private static void draw_rectangle(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        boolean filled = a.arguments.get(4).menuVal == 0;
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        if (filled)
            g.fillRect(x1, y1, (x2 - x1), (y2 - y1));
        else
            g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
    }

    private static void draw_gradient_hor(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        Color c1 = a.arguments.get(4).colorVal;
        Color c2 = a.arguments.get(5).colorVal;

        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }

        g.drawRectGradientHor(x1, y1, (x2 - x1), (y2 - y1), c1, c2);
    }

    private static void draw_gradient_vert(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        Color c1 = a.arguments.get(4).colorVal;
        Color c2 = a.arguments.get(5).colorVal;
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        g.drawRectGradientVert(x1, y1, (x2 - x1), (y2 - y1), c1, c2);
    }

    private static void draw_ellipse(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        boolean filled = a.arguments.get(4).menuVal == 0;
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        if (filled)
            g.fillOval(x1, y1, (x2 - x1), (y2 - y1));
        else
            g.drawOval(x1, y1, (x2 - x1), (y2 - y1));
    }

    private static void draw_ellipse_gradient(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        Color c1 = a.arguments.get(4).colorVal;
        Color c2 = a.arguments.get(5).colorVal;
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        g.drawOvalGradient(x1, y1, (x2 - x1), (y2 - y1), c1, c2);
    }

    private static void draw_line(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        g.drawLine(x1, y1, x2, y2);
    }

    private static void draw_arrow(Action a, Instance instance, Instance other) {
        double x1 = GmlParser.getExpression(a.arguments.get(0).exprVal, instance, other);
        double y1 = GmlParser.getExpression(a.arguments.get(1).exprVal, instance, other);
        double x2 = GmlParser.getExpression(a.arguments.get(2).exprVal, instance, other);
        double y2 = GmlParser.getExpression(a.arguments.get(3).exprVal, instance, other);
        double tipSize = GmlParser.getExpression(a.arguments.get(4).exprVal, instance, other);
        if (a.relative) {
            x1 += instance.x;
            x2 += instance.x;
            y1 += instance.y;
            y2 += instance.y;
        }
        g.drawArrow(x1, y1, x2, y2, tipSize);
    }

    private static void set_color(Action a, Instance instance, Instance other) {
        Color c = GmlParser.convertGmColor(Integer.parseInt(a.arguments.get(0).val));
        g.setColor(c);
    }

    private static void set_font(Action a, Instance instance, Instance other) {
        int fontId = a.arguments.get(0).resVal;
        g.fontAlign = a.arguments.get(1).menuVal;
        GameFont font = RuneroGame.game.getFont(fontId);
        if (font == null)
            return;
        g.setFont(font);
    }

    private static void fullscreen(Action a, Instance instance, Instance other) {
        // ? no idea how lol
        // menu (switch|window|fullscreen)
    }

    private static void take_snapshot(Action a, Instance instance, Instance other) {
        String fname = GmlParser.getExpressionString(a.arguments.get(0), instance, other);
        g.saveScreenshot(new File(fname));
    }

    private static void effect(Action a, Instance instance, Instance other) {
        // explosion|ring|ellipse|firework|smoke|smoke up|star|spark|flare|cloud|rain|snow
        // x, y, size (small|medium|large)
        // color
        // where (below objects|above objects)
    }
}