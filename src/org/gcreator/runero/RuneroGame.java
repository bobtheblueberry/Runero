package org.gcreator.runero;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;

import org.gcreator.runero.event.EventManager;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.gfx.RuneroDisplay;
import org.gcreator.runero.gfx.TextureLoader;
import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.gml.GmlParser;
import org.gcreator.runero.gml.ReferenceTable;
import org.gcreator.runero.gml.VariableVal;
import org.gcreator.runero.gml.exec.Variable;
import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.inst.RoomInstance;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFont;
import org.gcreator.runero.res.GameInformation;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GamePath;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameScript;
import org.gcreator.runero.res.GameSound;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameTimeline;
import org.lwjgl.input.Mouse;

/**
 * Game Engine base
 * 
 */
public class RuneroGame {

    public static RuneroGame game;
    public static RoomInstance room;
    public static RuneroDisplay display;
    public static TextureLoader tex;
    public EventManager em;

    public ArrayList<GameRoom> rooms;
    public ArrayList<GameBackground> backgrounds;
    public ArrayList<GameObject> objects;
    public ArrayList<GameSprite> sprites;
    public ArrayList<GameSound> sounds;
    public ArrayList<GamePath> paths;
    public ArrayList<GameScript> scripts;
    public ArrayList<GameFont> fonts;
    public ArrayList<GameTimeline> timelines;

    public GameInformation gameInfo;
    public ReferenceTable<VariableVal> globalVars;
    public ReferenceTable<VariableVal> globalDotVars;
    public ReferenceTable<VariableVal> constants;

    // Game Maker Global Game Variables....
    public double room_speed = 30;

    public double score; // The current score.
    public double lives; // Number of lives.
    public double health; // The current health (0-100).
    public boolean show_score;// Whether to show the score in the window caption.
    public boolean show_lives; // Whether to show the number of lives in the window caption.
    public boolean show_health; // Whether to show the health in the window caption.
    public String caption_score; // The caption used for the score.
    public String caption_lives; // The caption used for the number of lives.
    public String caption_health; // The caption used for the health.
    public int room_index; // the current room index; 'room' in GML
    public int cursor_sprite;

    public String keyboard_string = "";
    public int mouse_button;
    public int mouse_lastbutton;
    public int keyboard_key;
    public int keyboard_lastkey;
    public String keyboard_lastchar = "";

    public int fps = 100002;
    
    public RuneroGame()
        {
            super();
            RuneroGame.game = this;
            em = new EventManager();
            globalVars = new ReferenceTable<VariableVal>();
            globalDotVars = new ReferenceTable<VariableVal>();
            tex = new TextureLoader();
            gameStart();
        }

    public void loadGame() {

        // make sure there is rooms
        if (rooms.size() < 1) {
            System.err.println("No rooms! Exiting...");
            System.exit(1);
        }
        // Go to the first room
        room = new RoomInstance(this, rooms.get(0));
        GraphicsLibrary.gfx.setTitle(room.caption);
        room.init(true);
    }

    public void preload() {
        // TODO: find a better place for this
        // Load the images that are marked to preload
        for (Preloadable p : Runner.rl.preloadables) {
            p.load();
        }
    }

    // when game is started/restarted
    public void gameStart() {
        score = 0;
        lives = -1;
        health = 100;
        GraphicsLibrary.gfx.fontAlign = 0;
    }

    public GameBackground getBackground(int id) {
        for (GameBackground b : backgrounds) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    public GameObject getObject(int id) {
        for (GameObject o : objects) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public GameSprite getSprite(int id) {
        for (GameSprite s : sprites) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public GameFont getFont(int id) {
        for (GameFont f : fonts) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public void updateCaption() {
        // score lives health
        String caption = room.room.caption;
        int i = 0;
        if (show_score) {
            caption += " " + caption_score + score;
            i++;
        }
        if (show_lives) {
            caption += " " + caption_lives + lives;
            i++;
        }
        if (show_health) {
            caption += " " + caption_health + health;
            i++;
        }
        if (i > 0)
            GraphicsLibrary.gfx.setTitle(caption);
    }

    public void update() {
        room.step();
        updateCaption();
    }

    public void render() {
        room.render(GraphicsLibrary.gfx);
    }

    public VariableVal[] arguments;

    /**
     * Tries to set a variable is there is such.
     * For example, caption = "Billy May Craze"
     * 
     *  
     * @param v
     * @param val
     * @return whether not the variable was set
     */
    public boolean setVariable(Variable v, Constant val, Instance instance, Instance other) {
        String name = v.name;
        int i = 0;
        if (v.isArray)
            i = (int) v.arrayIndex.solve(instance, other).dVal;
        // TODO: argument[] argument0...15
        if (name.equals("caption")) {
            room.caption = val.sVal;
            return true;
        } else if (name.equals("score")) {
            score = val.dVal;
            return true;
        } else if (name.equals("room_speed")) {
            room_speed = val.dVal;
            return true;
        } else if (name.equals("room")) {
            // TODO: SET ROOM!!!
            room_index = (int) val.dVal;
            return true;
        } else if (name.equals("room_caption")) {
            room.caption = val.sVal;
            return true;
        } else if (name.equals("room_persistent")) {
            room.room.persistent = val.dVal > 0;
            return true;
        } else if (name.equals("score")) {
            score = val.dVal;
            return true;
        } else if (name.equals("lives")) {
            lives = val.dVal;
            return true;
        } else if (name.equals("health")) {
            health = val.dVal;
            return true;
        } else if (name.equals("show_score")) {
            show_score = val.dVal > 0.5;
            return true;
        } else if (name.equals("show_lives")) {
            show_lives = val.dVal > 0.5;
            return true;
        } else if (name.equals("show_health")) {
            show_health = val.dVal > 0.5;
            return true;
        } else if (name.equals("caption_score")) {
            caption_score = val.sVal;
            return true;
        } else if (name.equals("caption_lives")) {
            caption_lives = val.sVal;
            return true;
        } else if (name.equals("caption_health")) {
            caption_health = val.sVal;
            return true;
        } else if (name.equals("error_occurred")) {
            Runner.error_occurred = val.dVal > 0.5;
            return true;
        } else if (name.equals("error_last")) {
            Runner.error_last = val.sVal;
            return true;
        } else if (name.equals("keyboard_key")) {
            keyboard_key = (int) val.dVal;
            return true;
        } else if (name.equals("keyboard_lastkey")) {
            keyboard_lastkey = (int) val.dVal;
            return true;
        } else if (name.equals("keyboard_lastchar")) {
            keyboard_lastchar = val.sVal;
            return true;
        } else if (name.equals("keyboard_string")) {
            keyboard_string = val.sVal;
            return true;
        } else if (name.equals("mouse_button")) {
            mouse_button = (int) val.dVal;
            return true;
        } else if (name.equals("mouse_lastbutton")) {
            mouse_lastbutton = (int) val.dVal;
            return true;
        } else if (name.equals("cursor_sprite")) {
            // TODO: update cursor
            cursor_sprite = (int) val.dVal;
            return true;
        } else if (name.equals("background_color")) {
            room.background_color = new Color((int) val.dVal);
            return true;
        } else if (name.equals("background_showcolor")) {
            room.draw_background_color = val.dVal > 0.5;
            return true;
        } else if (name.equals("background_visible")) {
            room.backgrounds[i].visible = val.dVal > 0.5;
            return true;
        } else if (name.equals("background_foreground")) {
            room.backgrounds[i].foreground = val.dVal > 0.5;
            return true;
        } else if (name.equals("background_index")) {
            room.backgrounds[i].backgroundId = (int) val.dVal;
            return true;
        } else if (name.equals("background_x")) {
            room.backgrounds[i].x = (int) val.dVal;
            return true;
        } else if (name.equals("background_y")) {
            room.backgrounds[i].y = (int) val.dVal;
            return true;
        } else if (name.equals("background_width")) {
            // ERROR! cannot set
            System.err.println("Cannot set background width!");
            return true;
        } else if (name.equals("background_height")) {
            System.err.println("Cannot set background height!");
            return true;
        } else if (name.equals("background_htiled")) {
            room.backgrounds[i].tileHoriz = val.dVal > 0.5;
            return true;
        } else if (name.equals("background_vtiled")) {
            room.backgrounds[i].tileVert = val.dVal > 0.5;
            return true;
        } else if (name.equals("background_xscale")) {
            room.backgrounds[i].xscale = val.dVal;
            return true;
        } else if (name.equals("background_yscale")) {
            room.backgrounds[i].yscale = val.dVal;
            return true;
        } else if (name.equals("background_hspeed")) {
            room.backgrounds[i].hSpeed = val.dVal;
            return true;
        } else if (name.equals("background_vspeed")) {
            room.backgrounds[i].vSpeed = val.dVal;
            return true;
        } else if (name.equals("background_blend")) {
            room.backgrounds[i].blend = new Color((int) val.dVal);
            return true;
        } else if (name.equals("background_alpha")) {
            room.backgrounds[i].alpha = val.dVal;
            return true;
        }
        //TODO: views, tiles
        return false;
    }

    public VariableVal getVariable(Variable v, Instance instance, Instance other) {
        String name = v.name;
        if (v.isArray)
            name = GmlParser.getArrayName(v, instance, other);
        if (name.equals("gamemaker_registered")) {
            return VariableVal.ONE;
        }// else if (name.equals("argument_relative")) {
         // new VariableVal(argument_relative);
         // }
         // else if (name.equals("argument")) {
         // new VariableVal(arguments);
         // }

        // TODO: Fix arguments
        else if (name.equals("argument0")) {
            return arguments[0];
        } else if (name.equals("argument1")) {
            return arguments[1];
        } else if (name.equals("argument2")) {
            return arguments[2];
        } else if (name.equals("argument3")) {
            return arguments[3];
        } else if (name.equals("argument4")) {
            return arguments[4];
        } else if (name.equals("argument5")) {
            return arguments[5];
        } else if (name.equals("argument6")) {
            return arguments[6];
        } else if (name.equals("argument7")) {
            return arguments[7];
        } else if (name.equals("argument8")) {
            return arguments[8];
        } else if (name.equals("argument9")) {
            return arguments[9];
        } else if (name.equals("argument10")) {
            return arguments[10];
        } else if (name.equals("argument11")) {
            return arguments[11];
        } else if (name.equals("argument12")) {
            return arguments[12];
        } else if (name.equals("argument13")) {
            return arguments[13];
        } else if (name.equals("argument14")) {
            return arguments[14];
        } else if (name.equals("argument15")) {
            return arguments[15];
        }

        else if (name.equals("instance_count")) {
            return new VariableVal(room.getInstanceCount());
        } else if (name.equals("instance_id")) {// TODO: this is an array
            return new VariableVal(0);
        } else if (name.equals("room_speed")) {
            return new VariableVal(room_speed);
        } else if (name.equals("fps")) {
            return new VariableVal(fps);
        } else if (name.equals("current_time")) {
            return new VariableVal(System.currentTimeMillis() / 1000);
        } else if (name.equals("current_year")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.YEAR));
        } else if (name.equals("current_month")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.MONTH));
        } else if (name.equals("current_day")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        } else if (name.equals("current_weekday")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        } else if (name.equals("current_hour")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        } else if (name.equals("current_minute")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.MINUTE));
        } else if (name.equals("current_second")) {
            return new VariableVal(Calendar.getInstance().get(Calendar.SECOND));
        } else if (name.equals("room")) {
            return new VariableVal(room_index);
        } else if (name.equals("room_first")) {
            return new VariableVal(rooms.get(0).getId());
        } else if (name.equals("room_last")) {
            return new VariableVal(rooms.get(rooms.size()-1).getId());
        } else if (name.equals("room_width")) {
            return new VariableVal(room.width);
        } else if (name.equals("room_height")) {
            return new VariableVal(room.height);
        } else if (name.equals("room_caption")) {
            return new VariableVal(room.caption);
        } else if (name.equals("room_persistent")) {
            return VariableVal.Bool(room.room.persistent);
        } else if (name.equals("score")) {
            return new VariableVal(score);
        } else if (name.equals("lives")) {
            return new VariableVal(lives);
        } else if (name.equals("health")) {
            return new VariableVal(health);
        } else if (name.equals("show_score")) {
            return VariableVal.Bool(show_score);
        } else if (name.equals("show_lives")) {
            return VariableVal.Bool(show_lives);
        } else if (name.equals("show_health")) {
            return VariableVal.Bool(show_health);
        } else if (name.equals("caption_score")) {
            return new VariableVal(caption_score);
        } else if (name.equals("caption_lives")) {
            return new VariableVal(caption_lives);
        } else if (name.equals("caption_health")) {
            return new VariableVal(caption_health);
        } else if (name.equals("error_occurred")) {
            return VariableVal.Bool(Runner.error_occurred);
        } else if (name.equals("error_last")) {
            return new VariableVal(Runner.error_last);
        } else if (name.equals("keyboard_key")) {
            return new VariableVal('c');// TODO ...
        } else if (name.equals("keyboard_lastkey")) {
            return new VariableVal('c');
        } else if (name.equals("keyboard_lastchar")) {
            return new VariableVal('c');
        } else if (name.equals("keyboard_string")) {
            return new VariableVal("keyboard_string");// TODO
        } else if (name.equals("mouse_x")) {
            return new VariableVal(Mouse.getX());
        } else if (name.equals("mouse_y")) {
            return new VariableVal(Mouse.getY());
        } else if (name.equals("mouse_button")) {
            return new VariableVal(0);
        } else if (name.equals("mouse_lastbutton")) {
            return new VariableVal(0);
        } else if (name.equals("cursor_sprite")) {
            return new VariableVal(cursor_sprite);
        } else if (name.equals("background_color")) {
            return new VariableVal(room.background_color.getRGB());
        } else if (name.equals("background_showcolor")) {
            return VariableVal.Bool(room.draw_background_color);
        } else if (name.equals("game_id")) {
            return new VariableVal(0);// TODO: Game id
        } else if (name.equals("working_directory")) {
            return new VariableVal(System.getProperty("user.dir"));
        } else if (name.equals("temp_directory")) {
            return new VariableVal(System.getProperty("user.dir")); // TODO: Change
        } else if (name.equals("program_directory")) {
            return new VariableVal(System.getProperty("user.dir"));
        } else if (name.equals("secure_mode")) {
            return new VariableVal(0); // ff that stuff
        }
        
        return null;
    }
}
