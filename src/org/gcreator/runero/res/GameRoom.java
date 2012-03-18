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

    public String caption;
    public int speed;
    public boolean persistent;
    public boolean draw_background_color;
    public Code creation_code;
    public boolean enable_views;
    public Background[] backgrounds;
    public View[] views;
    public Tile[] tiles;
    public ArrayList<StaticInstance> staticInstances;

    public GameRoom(String name) {
        super(name);
    }

    public static class Background {
        public boolean visible;
        public boolean foreground;
        public int x;
        public int y;
        public boolean tileHoriz;
        public boolean tileVert;
        public int hSpeed;
        public int vSpeed;
        public boolean stretch;
        public int backgroundId;
    }

    public static class View {
        public boolean visible;
        public int viewX;
        public int viewY;
        public int viewW;
        public int viewH;
        public int portX;
        public int portY;
        public int portW;
        public int portH;
        public int borderH;
        public int borderV;
        public int speedH;
        public int speedV;
        public int objectId; // following object
    }

    public static class StaticInstance {
        public int x;
        public int y;
        public int objectId;
        public int id;
        public Code creationCode;
    }

    public static class Tile {
        public int bgX;
        public int bgY;
        public int roomX;
        public int roomY;
        public int width;
        public int height;
        public int depth;
        public int backgroundId;
        public int id;
        public boolean locked;
    }

}
