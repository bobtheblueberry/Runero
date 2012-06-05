package org.gcreator.runero.inst;

import java.awt.Color;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.Runner;
import org.gcreator.runero.event.ActionInterpreter;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.gfx.Texture;
import org.gcreator.runero.gml.Constant;
import org.gcreator.runero.gml.GmlParser;
import org.gcreator.runero.gml.ReferenceTable;
import org.gcreator.runero.gml.VariableVal;
import org.gcreator.runero.gml.exec.Variable;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameSprite.SubImage;

public class Instance implements Comparable<Instance> {

    public int alarm[] = new int[12];
    public double depth;

    private double direction, speed;               // use motion_set
    private double hspeed, vspeed;                 // use setSpeed, setVSpeed, setHSpeed

    public double friction;
    public double gravity;
    public double gravity_direction = 270;
    public int mask_index = -1;

    public double image_alpha = 1.0;
    public double image_angle;
    public Color image_blend;
    public double image_index;
    public double image_single;                   // Deprecated
    public double image_speed = 1.0;
    public double image_xscale = 1.0;
    public double image_yscale = 1.0;
    public double sprite_index;

    public double timeline_index;
    public double timeline_position;
    public double timeline_speed;

    public double path_endaction;
    public double path_index = -1;
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
    public int image_number = 0;          // * cannot be set manually
    public int id;

    public int parentId;                       // not accessed directly in GML, must use GML
    // function
    /**
     * When an instance is marked as dead then it does nothing and waits to be destroyed
     */
    public boolean isDead;

    public GameObject obj;
    public ReferenceTable<VariableVal> variables;

    public Instance(double x, double y, int id, GameObject obj)
        {
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
            this.variables = new ReferenceTable<VariableVal>();
            updateImageNumber();
        }

    public Instance(GameRoom.StaticInstance i)
        {
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
        ActionInterpreter.performEvent(event, this);
    }

    public GameSprite getSprite() {
        if (sprite_index < 0)
            return null;
        return RuneroGame.game.getSprite((int) Math.round(sprite_index));
    }

    public SubImage getSubImage() {
        GameSprite s = RuneroGame.game.getSprite((int) sprite_index);
        if (s == null)
            return null;
        int subs = s.subImages.size();
        if (subs == 0)
            return null;
        return s.subImages.get(((int) Math.round(image_index)) % s.subImages.size());
    }

    public GameSprite getMask() {
        if (mask_index < 0)
            return null;
        return RuneroGame.game.getSprite((int) Math.round(mask_index));
    }

    public void setDirection(double newDir) {
        motion_set(newDir, speed);
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

    public void draw(GraphicsLibrary g) {
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
        Texture img = s.getTexture((int) Math.round(image_index));
        if (img == null) {
            System.out.println("Null image for sprite " + s.getName() + " index " + image_index);
            return;
        }
        g.drawTexture(img, x, y, s.x, s.y, image_xscale, image_yscale, image_angle, image_blend, image_alpha);
        
        /** Code to draw mask over the sprite
        
        SubImage si = s.subImages.get(((int) Math.round(image_index))% s.subImages.size());
        g.setColor(Color.GREEN, 0.5);
        if (s.shape == org.gcreator.runero.res.GameSprite.MaskShape.PRECISE)
            for (int i = 0; i < s.width; i++)
                for (int j = 0; j < s.height; j++)
                   if (si.mask.map[i][j]) 
                        g.drawPoint((x - s.x) + i, (y - s.y) + j);
         */
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
        // Do it backwards (highest to lowest)
        return Double.compare(o.depth, depth);
    }

    public String toString() {
        return obj.getName() + " at (" + Math.round(x) + "," + Math.round(y) + ")";
    }

    /* Variable Code */
    public void setVariable(Variable v, Constant value, Instance other) {
        // TODO: Pre-runtime checking...
        String name = v.name;
        if (name.equals("path_index")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("object_index")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("id")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("sprite_width")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("sprite_height")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("sprite_xoffset")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("sprite_yoffset")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("image_number")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("bbox_left")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("bbox_right")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("bbox_top")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        } else if (name.equals("bbox_bottom")) {
            Runner.Error(this.toString() + ": Cannot set constant variable: " + name);
        }

        boolean builtin = false;
        if (name.equals("alarm")) {
            if (v.isArray) {
                Constant c = v.arrayIndex.solve(this, other);
                if (c.isString) {
                    Runner.Error("Invalid alarm index <String>");
                    return;
                }
                int index = (int) c.dVal;
                if (index > 11 || index < 0) {
                    Runner.Error("Invalid alarm index: " + index);
                    return;
                }
                alarm[index] = (int) value.dVal;
            } else
                alarm[0] = (int) value.dVal;
        } else if (name.equals("x")) {
            builtin = true;
            x = value.dVal;
        } else if (name.equals("y")) {
            builtin = true;
            y = value.dVal;
        } else if (name.equals("xprevious")) {
            builtin = true;
            xprevious = value.dVal;
        } else if (name.equals("yprevious")) {
            builtin = true;
            yprevious = value.dVal;
        } else if (name.equals("xstart")) {
            builtin = true;
            xstart = value.dVal;
        } else if (name.equals("ystart")) {
            builtin = true;
            ystart = value.dVal;
        } else if (name.equals("hspeed")) {
            builtin = true;
            setHspeed(value.dVal);
        } else if (name.equals("vspeed")) {
            builtin = true;
            setVspeed(value.dVal);
        } else if (name.equals("direction")) {
            builtin = true;
            setDirection(value.dVal);
        } else if (name.equals("speed")) {
            builtin = true;
            setSpeed(value.dVal);
        } else if (name.equals("friction")) {
            builtin = true;
            friction = value.dVal;
        } else if (name.equals("gravity")) {
            builtin = true;
            gravity = value.dVal;
        } else if (name.equals("gravity_direction")) {
            builtin = true;
            gravity_direction = value.dVal;
        } else if (name.equals("solid")) {
            builtin = true;
            solid = value.dVal > 0.5;
        } else if (name.equals("persistent")) {
            builtin = true;
            persistent = value.dVal > 0.5;
        } else if (name.equals("mask_index")) {
            builtin = true;
            mask_index = (int) Math.round(value.dVal);
        } else if (name.equals("timeline_index")) {
            builtin = true;
            timeline_index = value.dVal;
        } else if (name.equals("timeline_position")) {
            builtin = true;
            timeline_position = value.dVal;
        } else if (name.equals("timeline_speed")) {
            builtin = true;
            timeline_speed = value.dVal;
        } else if (name.equals("visible")) {
            builtin = true;
            visible = value.dVal > 0.5;
        } else if (name.equals("sprite_index")) {
            builtin = true;
            sprite_index = value.dVal;
            updateImageNumber();
        } else if (name.equals("image_index")) {
            builtin = true;
            image_index = value.dVal;
        } else if (name.equals("image_speed")) {
            builtin = true;
            image_speed = value.dVal;
        } else if (name.equals("depth")) {
            builtin = true;
            depth = value.dVal;
        } else if (name.equals("image_xscale")) {
            builtin = true;
            image_xscale = value.dVal;
        } else if (name.equals("image_yscale")) {
            builtin = true;
            image_yscale = value.dVal;
        } else if (name.equals("image_angle")) {
            builtin = true;
            image_angle = value.dVal;
        } else if (name.equals("image_alpha")) {
            builtin = true;
            image_alpha = value.dVal;
        } else if (name.equals("image_blend")) {
            builtin = true;
            image_blend = new Color((int) value.dVal);
        } else if (name.equals("image_single")) {
            builtin = true;
            image_single = value.dVal;
        }
        if (builtin && value.isString) {
            Runner.Error("Cannot set built-in object variable '" + name + " to <String>");
            return;
        }
        if (builtin)
            return;
        if (v.isArray)
            variables.put(GmlParser.getArrayName(v, this, other), new VariableVal(value));
        else
            variables.put(name, new VariableVal(value));

    }

    public VariableVal getVariable(Variable v, Instance other) {
        String name = v.name;
        if (v.isExpression) { // should not happen
            return null;
        }
        VariableVal val = getVar(name);
        if (val != null && v.isArray) { // x[123]
            System.out.println("Error! Cannot use built in variable as array");
            return val; // return val anyway
        } else if (val != null) {
            return val;
        }
        if (v.isArray)
            return variables.get(GmlParser.getArrayName(v, this, other));
        else
            return variables.get(name);

    }

    private VariableVal getVar(String name) {
        if (name.equals("x")) {
            return new VariableVal(x);
        } else if (name.equals("y")) {
            return new VariableVal(y);
        } else if (name.equals("xprevious")) {
            return new VariableVal(xprevious);
        } else if (name.equals("yprevious")) {
            return new VariableVal(yprevious);
        } else if (name.equals("xstart")) {
            return new VariableVal(xstart);
        } else if (name.equals("ystart")) {
            return new VariableVal(ystart);
        } else if (name.equals("hspeed")) {
            return new VariableVal(hspeed);
        } else if (name.equals("vspeed")) {
            return new VariableVal(vspeed);
        } else if (name.equals("direction")) {
            return new VariableVal(direction);
        } else if (name.equals("speed")) {
            return new VariableVal(speed);
        } else if (name.equals("friction")) {
            return new VariableVal(friction);
        } else if (name.equals("gravity")) {
            return new VariableVal(gravity);
        } else if (name.equals("gravity_direction")) {
            return new VariableVal(gravity_direction);
        } else if (name.equals("object_index")) {
            return new VariableVal(obj.getId());
        } else if (name.equals("id")) {
            return new VariableVal(id);
        } else if (name.equals("solid")) {
            return VariableVal.Bool(solid);
        } else if (name.equals("persistent")) {
            return VariableVal.Bool(persistent);
        } else if (name.equals("mask_index")) {
            return new VariableVal(mask_index);
        } else if (name.equals("timeline_index")) {
            return new VariableVal(timeline_index);
        } else if (name.equals("timeline_position")) {
            return new VariableVal(timeline_position);
        } else if (name.equals("timeline_speed")) {
            return new VariableVal(timeline_speed);
        } else if (name.equals("visible")) {
            return VariableVal.Bool(visible);
        } else if (name.equals("sprite_index")) {
            return new VariableVal(sprite_index);
        } else if (name.equals("sprite_width")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.width);
        } else if (name.equals("sprite_height")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.height);
        } else if (name.equals("sprite_xoffset")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.x);
        } else if (name.equals("sprite_yoffset")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.y);
        } else if (name.equals("image_number")) {
            return new VariableVal(image_number);
        } else if (name.equals("image_index")) {
            return new VariableVal(image_index);
        } else if (name.equals("image_speed")) {
            return new VariableVal(image_speed);
        } else if (name.equals("depth")) {
            return new VariableVal(depth);
        } else if (name.equals("image_xscale")) {
            return new VariableVal(image_xscale);
        } else if (name.equals("image_yscale")) {
            return new VariableVal(image_yscale);
        } else if (name.equals("image_angle")) {
            return new VariableVal(image_angle);
        } else if (name.equals("image_alpha")) {
            return new VariableVal(image_alpha);
        } else if (name.equals("image_blend")) {
            return new VariableVal((image_blend == null) ? 0 : image_blend.getRGB());
        } else if (name.equals("bbox_left")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.left);
        } else if (name.equals("bbox_right")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.right);
        } else if (name.equals("bbox_top")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.top);
        } else if (name.equals("bbox_bottom")) {
            GameSprite s = getSprite();
            if (s == null)
                return VariableVal.ZERO;
            return new VariableVal(s.bottom);
        } else if (name.equals("path_index")) {
            return new VariableVal(path_index);
        } else if (name.equals("path_position")) {
            return new VariableVal(path_position);
        } else if (name.equals("path_positionprevious")) {
            return new VariableVal(path_positionprevious);
        } else if (name.equals("path_speed")) {
            return new VariableVal(path_speed);
        } else if (name.equals("path_scale")) {
            return new VariableVal(path_scale);
        } else if (name.equals("path_orientation")) {
            return new VariableVal(path_orientation);
        } else if (name.equals("path_endaction")) {
            return new VariableVal(path_endaction);
        }
        return null;
    }

}
