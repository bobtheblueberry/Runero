package org.gcreator.runero;

// JFC
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import org.gcreator.runero.event.EventManager;
import org.gcreator.runero.gml.GmlLibrary;
import org.gcreator.runero.gml.Variable;
import org.gcreator.runero.inst.RoomInstance;
import org.gcreator.runero.res.*;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.*;

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
    public double room_speed;

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

    RuneroGameField playfield; // the game playfield

    SpriteGroup PLAYER_GROUP;
    SpriteGroup PROJECTILE_GROUP;
    SpriteGroup ENEMY_GROUP;

    AnimatedSprite plane;

    Timer moveTimer; // to set enemy behaviour
                     // for moving left to right, right to left

    ProjectileEnemyCollision collision;

    GameFont font;

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
        if (rooms.size() > 3)
            room = new RoomInstance(this, rooms.get(3));
        else
            room = new RoomInstance(this, rooms.get(0));
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
        // create the game playfield
        playfield = new RuneroGameField();

        // associate the playfield with a background
        // background = new
        // ImageBackground(getImage("resources/background.jpg"), 640, 480);
        // playfield.setBackground(background);

        // create our plane sprite
        plane = new AnimatedSprite(getImages("resources/plane2.png", 3, 1), 287.5, 390);
        plane.setAnimate(true);
        plane.setLoopAnim(true);

        // ///// create the sprite group ///////
        PLAYER_GROUP = new SpriteGroup("Player");
        // no need to set the background for each group, we delegated it to
        // playfield
        // PLAYER_GROUP.setBackground(background);
        PROJECTILE_GROUP = new SpriteGroup("Projectile");

        // add all groups into our playfield
        playfield.addGroup(PLAYER_GROUP);
        playfield.addGroup(PROJECTILE_GROUP);
        // use shortcut, creating group and adding it to playfield in one step
        ENEMY_GROUP = playfield.addGroup(new SpriteGroup("Enemy"));

        // ///// insert sprites into the sprite group ///////
        PLAYER_GROUP.add(plane);

        // inserts sprites in rows to ENEMY_GROUP
        BufferedImage image = getImage("resources/plane1.png");
        int startX = 10, startY = 30; // starting coordinate
        for (int j = 0; j < 4; j++) { // 4 rows
            for (int i = 0; i < 7; i++) { // 7 sprites in a row
                Sprite enemy = new Sprite(image, startX + (i * 80), startY + (j * 70));
                enemy.setHorizontalSpeed(0.04);
                ENEMY_GROUP.add(enemy);
            }
        }

        // init the timer to control enemy sprite behaviour
        // (moving left-to-right, right-to-left)
        moveTimer = new Timer(2000); // every 2 secs the enemies reverse its
                                     // speed

        // ///// register collision ///////
        collision = new ProjectileEnemyCollision(this);
        // register collision to playfield
        playfield.addCollisionGroup(PROJECTILE_GROUP, ENEMY_GROUP, collision);

        font = fontManager.getFont(getImages("resources/font.png", 20, 3), " !            .,0123"
                + "456789:   -? ABCDEFG" + "HIJKLMNOPQRSTUVWXYZ ");
    }

    public void update(long elapsedTime) {
        // no need to update the background and the group one by one
        // the playfield has taken this job!
        // background.update(elapsedTime);
        // PLAYER_GROUP.update(elapsedTime);
        // ENEMY_GROUP.update(elapsedTime);
        // PROJECTILE_GROUP.update(elapsedTime);

         collision.checkCollision();

        // playfield update all things and check for collision
        // playfield.update(elapsedTime);
        room.step();

        // enemy sprite movement timer
        if (moveTimer.action(elapsedTime)) {
            // reverse all enemies' speed
            Sprite[] sprites = ENEMY_GROUP.getSprites();
            int size = ENEMY_GROUP.getSize();

            // iterate the sprites
            for (int i = 0; i < size; i++) {
                // reverse sprite velocity
                sprites[i].setHorizontalSpeed(-sprites[i].getHorizontalSpeed());
            }
        }

        // control the sprite with arrow key
        double speedX = 0;
        double speedY = 0;
        if (keyDown(KeyEvent.VK_LEFT))
            speedX = -0.1;
        if (keyDown(KeyEvent.VK_RIGHT))
            speedX = 0.1;
        if (keyDown(KeyEvent.VK_UP))
            speedY = -0.1;
        if (keyDown(KeyEvent.VK_DOWN))
            speedY = 0.1;

        plane.setHorizontalSpeed(speedX);
        plane.setVerticalSpeed(speedY);

        // firing!!
        if (keyPressed(KeyEvent.VK_CONTROL)) {
            // create projectile sprite
            Sprite projectile = new Sprite(getImage("resources/projectile.png"));
            projectile.setLocation(plane.getX() + 16.5, plane.getY() - 16);
            projectile.setVerticalSpeed(-0.2);

            // add it to PROJECTILE_GROUP
            PROJECTILE_GROUP.add(projectile);

            // play fire sound
            playSound("resources/sound1.wav");
        }

        // toggle ppc
        if (keyPressed(KeyEvent.VK_ENTER)) {
            collision.pixelPerfectCollision = !collision.pixelPerfectCollision;
        }

        // background.setToCenter(plane);
    }

    public void render(Graphics2D g) {
        room.graphics = g;
        room.render(g);
        // playfield.render(g);

        // draw info text
        font.drawString(g, "ARROW KEY : MOVE", 10, 10);
        font.drawString(g, "CONTROL   : FIRE", 10, 30);
        font.drawString(g, "ENTER     : TOGGLE PPC", 10, 50);

        if (collision.pixelPerfectCollision) {
            font.drawString(g, " USE PIXEL PERFECT COLLISION ", GameFont.RIGHT, 0, 460, getWidth());
        } else {
            font.drawString(g, "SQUARE BOX COLLISION", GameFont.LEFT, 20, 460, getWidth());
        }
    }

}
