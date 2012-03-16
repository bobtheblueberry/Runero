package org.gcreator.runero;

// JFC
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.gcreator.runero.event.EventManager;
import org.gcreator.runero.gml.GmlLibrary;
import org.gcreator.runero.gml.Variable;
import org.gcreator.runero.inst.RoomInstance;
import org.gcreator.runero.res.*;

import com.golden.gamedev.Game;

/**
 * It's time to play!
 * 
 * Objective: show how to use playfield to automate all things!
 */
public class RuneroGame extends Game {

    public static RuneroGame game;
    public static RoomInstance room;
    public static GmlLibrary library;
    public EventManager eventManager;

    public File GameFolder = new File("/home/serge/Develop/ENIGMA/enigma-dev/RuneroGame");

    public int[] roomOrder;
    public ArrayList<GameRoom> rooms;
    public ArrayList<GameBackground> backgrounds;
    public ArrayList<GameObject> objects;
    public ArrayList<GameSprite> sprites;
    public ArrayList<GameSound> sounds;
    public ArrayList<GamePath> paths;
    public ArrayList<GameScript> scripts;
    public ArrayList<GameFontRes> fonts;
    public ArrayList<GameTimeline> timelines;

    public GameInformation gameInfo;
    public Hashtable<String, Variable> globalVars;

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
    public int fontAlign; // Font alignment, 0,1,2 (left,center,right), respectively


    public RuneroGame() {
        super();
        RuneroGame.game = this;
        library = new GmlLibrary();
        eventManager = new EventManager();
        globalVars = new Hashtable<String, Variable>();
        gameStart();
    }

    public void loadGame() {

        // make sure there is rooms
        if (rooms.size() < 1) {
            System.err.println("No rooms! Exiting...");
            System.exit(1);
        }
        // Go to the first room
        // this code is for ME, TESTING
//        if (rooms.size() > 3)
  //          room = new RoomInstance(this, rooms.get(3));
    //    else
            room = new RoomInstance(this, rooms.get(0));
        room.init(true);
    }

    public void start() {
        super.start();
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
        fontAlign = 0;
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

    public GameFontRes getFont(int id) {
        for (GameFontRes f : fonts) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }

    public void initResources() {
     //  System.out.println("nope, no init resources");
    }

    
    public void update() {
        room.step();
    }

    public void render(Graphics2D g) {
        room.graphics = g;
        room.render(g);
    }
}
