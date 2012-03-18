package org.gcreator.runero.inst;

import java.awt.Color;
import java.util.Hashtable;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.gml.GmlInterpreter;
import org.gcreator.runero.gml.Variable;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameSprite;
import org.newdawn.slick.opengl.Texture;

public class Instance implements Comparable<Instance> {

    public int alarm[] = new int[12];
    public double depth;

    private double direction, speed; // use motion_set
    private double hspeed, vspeed; // use setSpeed, setVSpeed, setHSpeed

    public double friction;
    public double gravity;
    public double gravity_direction = 270;
    public int mask_index = -1;

    public double image_alpha;
    public double image_angle;
    public Color image_blend;
    public double image_index;
    public double image_single; // Deprecated
    public double image_speed = 1.0;
    public double image_xscale = 1.0;
    public double image_yscale = 1.0;
    public double sprite_index;

    public double timeline_index;
    public double timeline_position;
    public double timeline_speed;

    public double path_endaction;
    public double path_index;
    public double path_orientation;
    public double path_position;
    public double path_positionprevious;
    public double path_scale;
    public double path_speed;
    public boolean persistent;
    public boolean solid;
    public boolean visible;
    public double x, y;
    public double xstart, ystart;
    public double xprevious, yprevious;
    public int image_number = 0; // * cannot be set manually

    public int parentId; // not accessed directly in GML, must use GML function
    /**
     * When an instance is marked as dead then it does nothing and waits to be destroyed
     */
    public boolean isDead;
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

    public GameObject obj;
    public int id;
    public Hashtable<String, Variable> variables;

    public Instance(double x, double y, int id, GameObject obj) {
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
        this.persistent = obj.persistent; // TODO: Persistence for objects
        this.parentId = obj.parentId;
        this.mask_index = obj.maskId;
        this.variables = new Hashtable<String, Variable>();
        updateImageNumber();
    }

    public Instance(GameRoom.StaticInstance i) {
        this(i.x, i.y, i.id, RuneroGame.game.getObject(i.objectId));
    }

    public void updateImageNumber() {
        GameSprite s = RuneroGame.game.getSprite((int) Math.round(sprite_index));
        if (s == null) {
            image_number = 0;
        } else {
            image_number = s.subImages.size();
        }
    }

    public GameSprite.SubImage getCurrentSubImg() {
        GameSprite s = getSprite();
        if (s == null)
            return null;
        return s.subImages.get(getImageIndex());
    }

    public int getImageIndex() {
        if (sprite_index >= 0)
            image_index %= image_number;
        return (int) Math.round(image_index);
    }

    /**
     * Performs event
     * 
     * @param index  main event index
     */
    public void performEvent(Event event) {
        if (isDead)
            return;
        byte index = event.parent.mainEvent;
        if (index == MainEvent.EV_DRAW) {
            if (!visible)
                return;
        }
        if (index == MainEvent.EV_COLLISION)
            if (solid || event.collisionObject.solid) {
                x = xprevious;
                y = yprevious;
            }

        GmlInterpreter.performEvent(event, this);
    }

    public GameSprite getSprite() {
        if (sprite_index < 0)
            return null;
        return RuneroGame.game.getSprite((int) Math.round(sprite_index));
    }

    public GameSprite getMask() {
        if (mask_index < 0)
            return null;
        return RuneroGame.game.getSprite((int) Math.round(mask_index));
    }

    public void setSpeed(double newSpeed) {
        motion_set(direction, newSpeed);
    }

    public void setHspeed(double newHspeed) {
        setSpeed(newHspeed, vspeed);
    }

    public void setVspeed(double newVspeed) {
        setSpeed(hspeed, newVspeed);
    }

    public void setSpeed(double newHspeed, double newVspeed) {
        hspeed = newHspeed;
        vspeed = newVspeed;
        double dir = Math.atan2(newVspeed, newHspeed);
        this.direction = dir / Math.PI * 180;
        // Pythagorean theorem
        double h = Math.abs(newHspeed);
        double v = Math.abs(newVspeed);
        this.speed = Math.sqrt((v * v) + (h * h));
    }

    public void motion_set(double dir, double speed) {
        this.direction = dir;
        this.speed = speed;
        double r = dir * Math.PI / 180;
        double cos = Math.cos(r);
        double sin = Math.sin(r);
        hspeed = cos * speed;
        vspeed = -sin * speed;
    }

    public void motion_add(double dir, double speed) {
        motion_set(dir + this.direction, speed + this.speed);
    }

    protected void move() {
        if (isDead)
            return;
        xprevious = x;
        yprevious = y;
        // Move with direction etc
        x += hspeed;
        y += vspeed;
        image_index += image_speed;
        if (sprite_index >= 0)
            image_index %= image_number;
    }

    protected void draw(GraphicsLibrary g) {
        if (isDead)
            return;
        if (sprite_index < 0) {
            return;
        }
        GameSprite s = RuneroGame.game.getSprite((int) sprite_index);
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
        Texture img = s.getSubImage((int) Math.round(image_index));
        // TODO: angle, scale, color blend
        if (img == null) {
            System.out.println("Null image for sprite " + s.getName() + " index " + image_index);
            return;
        }
        g.drawTexture(img,  x - s.x, y - s.y);
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public double getHspeed() {
        return hspeed;
    }

    public double getVspeed() {
        return vspeed;
    }

    public boolean equals(Object o) {
        if (o instanceof Instance) {
            Instance i = (Instance) o;
            return i.id == this.id;
        }
        return false;
    }

    @Override
    public int compareTo(Instance o) {
        return Double.compare(depth, o.depth);
    }

    public String toString() {
        return obj.getName() + " at (" + x + "," + y + ")";
    }
}
