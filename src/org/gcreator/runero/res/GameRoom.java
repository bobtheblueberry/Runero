package org.gcreator.runero.res;

import java.awt.Color;
import java.util.ArrayList;

public class GameRoom extends GameResource {

    public Color background_color;
    protected int width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public String                    caption;
    public int                       speed;
    public boolean                   persistent;
    public boolean                   draw_background_color;
    public CodeRes                      creation_code;
    public boolean                   enable_views;
    public Background[]              backgrounds;
    public View[]                    views;
    public Tile[]                    tiles;
    public ArrayList<StaticInstance> staticInstances;

    public GameRoom(String name)
        {
            super(name);
        }

    public static class Background {
        public boolean visible;
        public boolean foreground;
        public int     x;
        public int     y;
        public boolean tileHoriz;
        public boolean tileVert;
        public double     hSpeed;
        public double     vSpeed;
        public boolean stretch;
        public int     backgroundId;
        public double xscale = 1.0;
        public double yscale = 1.0;
        public Color blend;
        public double alpha = 1.0;

        public Background copy() {
            Background b = new Background();
            b.visible = visible;
            b.foreground = foreground;
            b.x = x;
            b.y = y;
            b.tileHoriz = tileHoriz;
            b.tileVert = tileVert;
            b.hSpeed = hSpeed;
            b.vSpeed = vSpeed;
            b.stretch = stretch;
            b.backgroundId = backgroundId;
            b.xscale = xscale;
            b.yscale = yscale;
            b.blend = blend;
            b.alpha = alpha;
            return b;
        }
    }

    public static class View {
        public boolean visible;
        public int     viewX;
        public int     viewY;
        public int     viewW;
        public int     viewH;
        public int     portX;
        public int     portY;
        public int     portW;
        public int     portH;
        public int     borderH;
        public int     borderV;
        public int     speedH;
        public int     speedV;
        public int     objectId; // following object

        public View copy() {
            View v = new View();
            v.visible = visible;
            v.viewX = viewX;
            v.viewY = viewY;
            v.viewW = viewW;
            v.viewH = viewH;
            v.portX = portX;
            v.portY = portY;
            v.portW = portW;
            v.portH = portH;
            v.borderH = borderH;
            v.borderV = borderV;
            v.speedH = speedH;
            v.speedV = speedV;
            v.objectId = objectId;
            return v;
        }
    }

    public static class StaticInstance {
        public int  x;
        public int  y;
        public int  objectId;
        public int  id;
        public CodeRes creationCode;
    }

    public static class Tile {
        public int     bgX;
        public int     bgY;
        public int     roomX;
        public int     roomY;
        public int     width;
        public int     height;
        public int     depth;
        public int     backgroundId;
        public int     id;
        public boolean locked;

        public Tile copy() {
            Tile t = new Tile();
            t.bgX = bgX;
            t.bgY = bgY;
            t.roomX = roomX;
            t.roomY = roomY;
            t.width = width;
            t.height = height;
            t.depth = depth;
            t.backgroundId = backgroundId;
            t.id = id;
            t.locked = locked;
            
            return t;
        }
    }

}
