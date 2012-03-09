package org.gcreator.runero.gml.lib;

import java.util.ArrayList;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Action;
import org.gcreator.runero.gml.GmlParser;
import org.gcreator.runero.gml.ReturnValue;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameSprite;

/**
 * Implements all lib actions
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
    public static final int SET_SPRITE_OLD = 205; // DEPRECATED
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
    public static final int START_BLOCK = 422; // not used
    public static final int ELSE = 421; // not used
    public static final int EXIT = 425; // not used
    public static final int END_BLOCK = 424; // useless
    public static final int REPEAT = 423; // very useless
    public static final int INHERITED = 604;
    public static final int CODE = 603; // not actually used
    public static final int EXECUTE_SCRIPT = 601;
    public static final int COMMENT = 605;
    public static final int VARIABLE = 611; // not needed
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
    public static final int PART_TYPE_CREATE_OLD = 825; // DEPRECATED
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

    public static ReturnValue executeAction(Action act, Instance instance) {
        return executeAction(act, instance, null);
    }

    /**
     * ActionLibrary doesn't deal with silly function names, etc. It just looks
     * up the action ID to see what to do.
     * 
     * @param instance
     * @param act
     * @return
     */
    public static ReturnValue executeAction(Action act, Instance instance, Instance other) {
        if (act.lib.question) {
            System.err.println("Error! Game Engine used wrong function to execute action");
            return ReturnValue.FAILURE;
        }
        if (act.lib.execType == Action.EXEC_NONE) {
            if (act.lib.id != COMMENT) {
                System.out.println("?? Nothing action != Comment ?? " + act.lib.name);
            }
            return ReturnValue.SUCCESS;
        }
        
        switch (act.lib.id) {
        // move
        case ACTION_MOVE:
            action_move(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_MOTION:
            set_motion(act, instance, other);
            return ReturnValue.SUCCESS;
        case MOVE_POINT:
            move_point(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_HSPEED:
            set_hspeed(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_VSPEED:
            set_vspeed(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_GRAVITY:
            set_gravity(act, instance, other);
            return ReturnValue.SUCCESS;
        case REVERSE_XDIR:
            reverse_xdir(act, instance, other);
            return ReturnValue.SUCCESS;
        case REVERSE_YDIR:
            reverse_ydir(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_FRICTION:
            set_friction(act, instance, other);
            return ReturnValue.SUCCESS;
        case MOVE_TO:
            move_to(act, instance, other);
            return ReturnValue.SUCCESS;
        case MOVE_START:
            move_start(act, instance, other);
            return ReturnValue.SUCCESS;
        case MOVE_RANDOM:
            move_random(act, instance, other);
            return ReturnValue.SUCCESS;
        case SNAP:
            snap(act, instance, other);
            return ReturnValue.SUCCESS;
        case WRAP:
            wrap(act, instance, other);
            return ReturnValue.SUCCESS;
        case MOVE_CONTACT:
            // TODO: This, and collision system
            return ReturnValue.FAILURE;
        case BOUNCE:
            // TODO: This, and collision system
            return ReturnValue.FAILURE;
        case PATH:
            // TODO: Paths
            return ReturnValue.FAILURE;
        case PATH_END:

            return ReturnValue.FAILURE;
        case PATH_POSITION:

            return ReturnValue.FAILURE;
        case PATH_SPEED:

            return ReturnValue.FAILURE;
        case STEP_LINEAR:
            // TODO: Stepping
            return ReturnValue.FAILURE;
        case STEP_POTENTIAL:

            return ReturnValue.FAILURE;
            // main1
        case CREATE_OBJECT:
            create_object(act, instance, other);
            return ReturnValue.SUCCESS;
        case CREATE_OBJECT_MOTION:
            create_object_motion(act, instance, other);
            return ReturnValue.SUCCESS;
        case CREATE_OBJECT_RANDOM:
            create_object_random(act, instance, other);
            return ReturnValue.SUCCESS;
        case CHANGE_OBJECT:
            change_object(act, instance, other);
            return ReturnValue.SUCCESS;
        case KILL_OBJECT:
            kill_object(act, instance, other);
            return ReturnValue.SUCCESS;
        case KILL_POSITION: // TODO: Implement this!
            kill_position(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_SPRITE:
            set_sprite(act, instance, other);
            return ReturnValue.SUCCESS;
        case TRANSFORM_SPRITE:
            transform_sprite(act, instance, other);
            return ReturnValue.SUCCESS;
        case COLOR_SPRITE:
            color_sprite(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_SPRITE_OLD: // Deprecated
            set_sprite_old(act, instance, other);
            return ReturnValue.SUCCESS;
        case BEGIN_SOUND:

            return ReturnValue.FAILURE;
        case END_SOUND:

            return ReturnValue.FAILURE;
        case PREVIOUS_ROOM:

            return ReturnValue.FAILURE;
        case NEXT_ROOM:

            return ReturnValue.FAILURE;
        case CURRENT_ROOM:

            return ReturnValue.FAILURE;
        case ANOTHER_ROOM:

            return ReturnValue.FAILURE;
            // main2
        case SET_ALARM:

            return ReturnValue.FAILURE;
        case SLEEP:
            sleep(act, instance, other);
            return ReturnValue.SUCCESS;
        case SET_TIMELINE:

            return ReturnValue.FAILURE;
        case POSITION_TIMELINE:

            return ReturnValue.FAILURE;
        case MESSAGE:

            return ReturnValue.FAILURE;
        case SHOW_INFO:
            show_info(act, instance, other);
            return ReturnValue.SUCCESS;
        case SHOW_VIDEO:

            return ReturnValue.FAILURE;
        case RESTART_GAME:

            return ReturnValue.FAILURE;
        case END_GAME:

            return ReturnValue.FAILURE;
        case SAVE_GAME:

            return ReturnValue.FAILURE;
        case LOAD_GAME:

            return ReturnValue.FAILURE;
        case REPLACE_SPRITE:

            return ReturnValue.FAILURE;
        case REPLACE_SOUND:

            return ReturnValue.FAILURE;
        case REPLACE_BACKGROUND:

            return ReturnValue.FAILURE;
            // control
        case INHERITED:

            return ReturnValue.FAILURE;
        case CODE:

            return ReturnValue.FAILURE;
        case EXECUTE_SCRIPT:

            return ReturnValue.FAILURE;
        case DRAW_VARIABLE:

            return ReturnValue.FAILURE;
            // score
        case SET_SCORE:

            return ReturnValue.FAILURE;

        case DRAW_SCORE:

            return ReturnValue.FAILURE;
        case HIGHSCORE_SHOW:

            return ReturnValue.FAILURE;
        case HIGHSCORE_CLEAR:

            return ReturnValue.FAILURE;
        case SET_LIFE:

            return ReturnValue.FAILURE;
        case DRAW_LIFE:

            return ReturnValue.FAILURE;
        case DRAW_LIFE_IMAGES:

            return ReturnValue.FAILURE;
        case SET_HEALTH:

            return ReturnValue.FAILURE;
        case DRAW_HEALTH:

            return ReturnValue.FAILURE;
        case SET_CAPTION:

            return ReturnValue.FAILURE;
            // extra i.e. never going to be implemented
        case PART_SYST_CREATE:

            return ReturnValue.FAILURE;
        case PART_SYST_DESTROY:

            return ReturnValue.FAILURE;
        case PART_SYST_CLEAR:

            return ReturnValue.FAILURE;
        case PART_TYPE_CREATE_OLD:

            return ReturnValue.FAILURE;
        case PART_TYPE_CREATE:

            return ReturnValue.FAILURE;
        case PART_TYPE_COLOR:

            return ReturnValue.FAILURE;
        case PART_TYPE_LIFE:

            return ReturnValue.FAILURE;
        case PART_TYPE_SPEED:

            return ReturnValue.FAILURE;
        case PART_TYPE_GRAVITY:

            return ReturnValue.FAILURE;
        case PART_TYPE_SECONDARY:

            return ReturnValue.FAILURE;
        case PART_EMIT_CREATE:

            return ReturnValue.FAILURE;
        case PART_EMIT_DESTROY:

            return ReturnValue.FAILURE;
        case PART_EMIT_BURST:

            return ReturnValue.FAILURE;
        case PART_EMIT_STREAM:

            return ReturnValue.FAILURE;
        case CD_PLAY:

            return ReturnValue.FAILURE;
        case CD_STOP:

            return ReturnValue.FAILURE;
        case CD_PAUSE:

            return ReturnValue.FAILURE;
        case CD_RESUME:

            return ReturnValue.FAILURE;
        case SET_MOUSE:

            return ReturnValue.FAILURE;
        case OPEN_WEBPAGE:

            return ReturnValue.FAILURE;
            // draw
        case DRAW_SPRITE:

            return ReturnValue.FAILURE;
        case DRAW_BACKGROUND:

            return ReturnValue.FAILURE;
        case DRAW_TEXT:

            return ReturnValue.FAILURE;
        case DRAW_TEXT_SCALED:

            return ReturnValue.FAILURE;
        case DRAW_RECTANGLE:

            return ReturnValue.FAILURE;
        case DRAW_GRADIENT_HOR:

            return ReturnValue.FAILURE;
        case DRAW_GRADIENT_VERT:

            return ReturnValue.FAILURE;
        case DRAW_ELLIPSE:

            return ReturnValue.FAILURE;
        case DRAW_ELLIPSE_GRADIENT:

            return ReturnValue.FAILURE;
        case DRAW_LINE:

            return ReturnValue.FAILURE;
        case DRAW_ARROW:

            return ReturnValue.FAILURE;
        case SET_COLOR:

            return ReturnValue.FAILURE;
        case SET_FONT:

            return ReturnValue.FAILURE;
        case FULLSCREEN:

            return ReturnValue.FAILURE;
        case TAKE_SNAPSHOT:

            return ReturnValue.FAILURE;
        case EFFECT:

            return ReturnValue.FAILURE;
        }
        return ReturnValue.FAILURE;
    }

    // There are 18 question actions as of GM7
    public static boolean executeQuestion(Action act, Instance instance, Instance other) {
        switch (act.lib.id) {
        case IF_EMPTY:

            return false;
        case IF_COLLISION:
            
            return false;
        case IF_OBJECT:

            return false;
        case IF_NUMBER:

            return false;
        case IF_DICE:

            return false;
        case IF_QUESTION:

            return false;
        case IF:

            return false;
        case IF_MOUSE:

            return false;
        case IF_ALIGNED:
            return false;
        case IF_SOUND:

            return false;
        case IF_SCORE:
            return false;
        case IF_VARIABLE:

            return false;
        case IF_LIFE:

            return false;
        case IF_HEALTH:
            return false;
        case IF_PREVIOUS_ROOM:

            return false;
        case IF_NEXT_ROOM:

            return false;
        case CD_IF_EXISTS: // use executeQuestion()

            return false;
        case CD_IF_PLAYING: // use executeQuestion()

            return false;

        }
        System.out.println("Error! $14F");
        return false;
    }

    private static void action_move(Action a, Instance instance, Instance other) {
        // Move Fixed..
        // Arrows.. yay
        String arg0 = a.arguments.get(0).val;
        String arg1 = a.arguments.get(1).val;
        double speed = GmlParser.getExpression(arg1, instance, other);
        ArrayList<Integer> vals = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            if (!arg0.substring(i).equals("1"))
                continue;
            vals.add(i);
        }
        if (vals.size() == 0) {
            instance.setSpeed(speed);
            return;
        }
        int r = (int) (Math.random() * vals.size());
        int x = vals.get(r);
        if (x == 4) { // stop (middle button)
            instance.setSpeed(0);
            return;
        }
        int d = 0;
        if (r == 0)
            d = 315;
        else if (r == 1)
            d = 270;
        else if (r == 2)
            d = 225;
        else if (r == 3)
            d = 0;
        else if (r == 5)
            d = 180;
        else if (r == 6)
            d = 45;
        else if (r == 7)
            d = 90;
        else if (r == 8)
            d = 135;
        if (a.relative)
            speed += instance.getSpeed();
        instance.motion_set(d, speed);
    }

    private static void set_motion(Action a, Instance instance, Instance other) {
        double direction = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        if (a.relative)
            instance.motion_add(direction, speed);
        else
            instance.motion_set(direction, speed);
    }

    private static void move_point(Action a, Instance instance, Instance other) {
        // x,y,speed
        double x = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(2).val, instance, other);
        double dir;
        if (a.relative) {
            dir = MathLibrary.point_direction(instance.x, instance.y, instance.x + x, instance.y + y);
        } else {
            dir = MathLibrary.point_direction(instance.x, instance.y, x, y);
        }
        instance.motion_set(dir, speed);
    }

    private static void set_hspeed(Action a, Instance instance, Instance other) {
        double hspeed = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        if (a.relative)
            instance.setHspeed(instance.getHspeed() + hspeed);
        else
            instance.setHspeed(hspeed);
    }

    private static void set_vspeed(Action a, Instance instance, Instance other) {
        double vspeed = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        if (a.relative)
            instance.setVspeed(instance.getHspeed() + vspeed);
        else
            instance.setVspeed(vspeed);
    }

    private static void set_gravity(Action a, Instance instance, Instance other) {
        double direction = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double gravity = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
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
        double friction = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        instance.friction = friction;
    }

    private static void move_to(Action a, Instance instance, Instance other) {
        double x = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
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

        double xsnap = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double ysnap = GmlParser.getExpression(a.arguments.get(1).val, instance, other);

        double rx = Math.random() * RuneroGame.room.room.width;
        double ry = Math.random() * RuneroGame.room.room.height;
        if (xsnap > 0)
            rx = ((int) (rx / xsnap)) * xsnap;
        if (ysnap > 0)
            ry = ((int) (ry / ysnap)) * ysnap;
        instance.x = rx;
        instance.y = ry;
    }

    private static void snap(Action a, Instance instance, Instance other) {
        double h = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double v = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double newX = Math.round(instance.x / h);
        double newY = Math.round(instance.y / v);
        instance.x = newX * h;
        instance.y = newY * v;
    }

    private static void wrap(Action a, Instance instance, Instance other) {
        // horizontal|vertical|in both directions
        int dir = Integer.parseInt(a.arguments.get(0).val);
        if (dir == 0 || dir == 2) {
            double newx = instance.x;
            int w = RuneroGame.room.room.width;
            // TODO: take the sprite into account
            while (newx < 0)
                newx = w + newx;
            while (newx > w)
                newx %= w;
            instance.x = newx;
        }
        if (dir == 1 || dir == 2) {
            double newy = instance.y;
            int h = RuneroGame.room.room.height;
            while (newy < 0)
                newy = h + newy;
            while (newy > h)
                newy %= h;
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
        int id = Integer.parseInt(a.arguments.get(0).val);
        if (id < 0) {
            System.out.println("cannot create object with id " + id);
            return;
        }
        GameObject o = RuneroGame.game.getObject(id);
        if (o == null) {
            System.out.println("Unknown object " + id);
            return;
        }
        double x = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).val, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y);
    }

    private static void create_object_motion(Action a, Instance instance, Instance other) {
        int id = Integer.parseInt(a.arguments.get(0).val);
        if (id < 0) {
            System.out.println("cannot create object with id " + id);
            return;
        }
        GameObject o = RuneroGame.game.getObject(id);
        if (o == null) {
            System.out.println("Unknown object " + id);
            return;
        }
        double x = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(2).val, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(3).val, instance, other);
        double dir = GmlParser.getExpression(a.arguments.get(4).val, instance, other);

        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y, dir, speed);
    }

    private static void create_object_random(Action a, Instance instance, Instance other) {
        int[] ids = new int[4];
        ids[0] = Integer.parseInt(a.arguments.get(0).val);
        ids[1] = Integer.parseInt(a.arguments.get(1).val);
        ids[2] = Integer.parseInt(a.arguments.get(2).val);
        ids[3] = Integer.parseInt(a.arguments.get(3).val);
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
        double x = GmlParser.getExpression(a.arguments.get(4).val, instance, other);
        double y = GmlParser.getExpression(a.arguments.get(5).val, instance, other);
        if (a.relative) {
            x += instance.x;
            y += instance.y;
        }
        RuneroGame.room.addInstace(o, x, y);
    }

    private static void change_object(Action a, Instance instance, Instance other) {
        int id = Integer.parseInt(a.arguments.get(0).val);
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
        int sprite = Integer.parseInt(a.arguments.get(0).val);
        double subimg = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double speed = GmlParser.getExpression(a.arguments.get(2).val, instance, other);
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
        double xscale = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        double yscale = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        double angle = GmlParser.getExpression(a.arguments.get(2).val, instance, other);
        int mirror = Integer.parseInt(a.arguments.get(3).val);
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
        int color = Integer.parseInt(a.arguments.get(0).val);
        double alpha = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        instance.image_blend = GmlParser.convertGmColor(color);
        instance.image_alpha = alpha;
    }

    // not used anymore
    private static void set_sprite_old(Action a, Instance instance, Instance other) {
        int sprite = Integer.parseInt(a.arguments.get(0).val);
        double scale = GmlParser.getExpression(a.arguments.get(1).val, instance, other);
        instance.sprite_index = sprite;
        instance.image_xscale = scale;
        instance.image_yscale = scale;
    }

    /*BEGIN_SOUND
    END_SOUND
    IF_SOUND
    PREVIOUS_ROOM
    NEXT_ROOM
    CURRENT_ROOM
    ANOTHER_ROOM
    IF_PREVIOUS_ROOM
    IF_NEXT_ROOM*/
    // main2
    /*SET_ALARM*/
    private static void sleep(Action a, Instance instance, Instance other) {
        double seconds = GmlParser.getExpression(a.arguments.get(0).val, instance, other);
        int redraw = Integer.parseInt(a.arguments.get(0).val);
        if (redraw == 1) {
            RuneroGame.game.bsGraphics.getComponent().repaint();
        }
        try {
            Thread.sleep((long) seconds);
        } catch (InterruptedException e) {
            System.out.println("Somebody bitched while game slept.");
        }
    }

    /*
    SLEEP
    SET_TIMELINE
    POSITION_TIMELINE
    MESSAGE*/
    private static void show_info(Action a, Instance instance, Instance other) {
        RuneroGame.game.gameInfo.showInfoWindow();
    }
}