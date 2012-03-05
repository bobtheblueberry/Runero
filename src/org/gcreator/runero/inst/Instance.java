package org.gcreator.runero.inst;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gml.GmlInterpreter;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameSprite;

public class Instance {

    int alarm[] = new int[12];
    double depth;
    double direction;
    double friction;
    double gravity;
    double gravity_direction = 270;
    double hspeed;
    double vspeed;
    double speed;
    int mask_index = -1;

    double image_alpha;
    double image_angle;
    double image_index;
    double image_single; // Deprecated
    double image_speed;
    double image_xscale = 1.0;
    double image_yscale = 1.0;
    double sprite_index;

    double timeline_index;
    double timeline_position;
    double timeline_speed;

    double path_endaction;
    double path_index;
    double path_orientation;
    double path_position;
    double path_positionprevious;
    double path_scale;
    double path_speed;
    boolean persistent;
    boolean solid;
    boolean visible;
    double x, y;
    double xstart, ystart;
    double xprevious, yprevious;
    
    int parentId; // not accessed directly in GML, must use function
    /*
    object_index*
    id*
    bbox_bottom*
    bbox_left*
    bbox_right*
    bbox_top*

    image_number*

    sprite_height*
    sprite_width*
    sprite_xoffset*
    sprite_yoffset*

     * */
    
    GameObject obj;
    int id;
    public Instance(int x, int y, int id, GameObject obj) {
        this.id = id;
        this.obj = obj;
        this.x = x;
        this.y = y;
        this.xstart = x;
        this.ystart = y;
        // Object variables
        this.sprite_index = obj.spriteId;
        this.solid = obj.solid;
        this.visible = obj.visible;
        this.depth = obj.depth;
        this.persistent = obj.persistent; //TODO: Persistence for objects
        this.parentId = obj.parentId;
        this.mask_index = obj.maskId;
    }
    
    public Instance(GameRoom.StaticInstance i) {
        this(i.x,i.y, i.id, RuneroGame.game.getObject(i.objectId));
    }
    
    public GameObject getObject() {
        return obj;
    }
    
    /**
     * Performs event
     * 
     * 
     * @param index main event index
     */
    public void performEvent(Event event) {
        byte index = event.parent.mainEvent;
        if (index == MainEvent.EV_DRAW) {
            if (!visible)
                return;
        }
        if (obj.hasEvent(index)) {
            MainEvent me = obj.getMainEvent(index);
            for (Event e : me.events) {
                for (Action a : e.actions) {
                    GmlInterpreter.performAction(a, this, e);
                }
            }
        }
    }
    
    public void setSpeed(double newSpeed) {
        motion_set(direction, newSpeed);
    }
    
    public void motion_set(double dir, double speed) {
        this.direction = dir;
        this.speed = speed;
        double r = dir*Math.PI / 180;
        double cos = Math.cos(r);
        double sin = Math.sin(r);
        hspeed = cos*speed;
        vspeed = -sin*speed;
    }
    
    public void motion_add(double dir, double speed) {
        motion_set(dir + this.direction, speed + this.speed);
    }
    
    protected void move() {
        xprevious = x;
        yprevious = y;
        //Move with direction etc
        x += hspeed;
        y += vspeed;
        
        //TODO: update sprite subimage
    }
    
    protected void draw() {
        //TODO: Draw  the sprite
        Graphics2D g = RuneroGame.room.graphics;
        if (g == null) {
            System.err.println("Room draw error! null graphics");
            return;
        }
        if (sprite_index < 0) { 
            return;
        }
        GameSprite s = RuneroGame.game.getSprite((int)sprite_index);
        if (s == null) {
            System.out.println("Unknown sprite " + sprite_index);
            return;
        }
        int subs = s.subImages.size(); 
        if (subs == 0) {
            System.out.println("Sprite has no sub-images " + s.getName());
            return;
        }
        s.load(); // load sub image files
        BufferedImage img = s.getSubImage((int)sprite_index);
        //TODO: angle, scale, color blend
        if (img == null) {
            System.out.println("Null image for sprite " + s.getName() + " index " + sprite_index);
            return;
        }
        g.drawImage(img, (int) x, (int) y, null);
    }

    public double getSpeed() {
        return speed;
    }
    
    public double getDirection() {
        return direction;
    }
}
