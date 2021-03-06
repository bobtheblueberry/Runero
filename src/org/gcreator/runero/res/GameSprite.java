package org.gcreator.runero.res;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.gcreator.runero.Preloadable;
import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.gfx.Texture;

public class GameSprite extends GameResource implements Preloadable {

    public enum MaskShape {
        PRECISE, RECTANGLE, DISK, DIAMOND, POLYGON
    }

    public MaskShape shape;
    public boolean transparent;
    public int alpha_tolerance;// GM8; Ignored
    public boolean mask;// GM8; Ignored
    public boolean smooth;
    public boolean preload;
    public int x, y;
    public int left, right, top, bottom;
    public int width, height;
    public ArrayList<SubImage> subImages;
    private boolean loaded = false;
    private Rectangle bounds;

    public GameSprite(String name)
        {
            super(name);
            subImages = new ArrayList<GameSprite.SubImage>();
        }

    public Rectangle getBounds() {
        if (bounds == null) {
            bounds = new Rectangle(Math.abs(right - left), Math.abs(bottom - top));
            bounds.translate(left, top);
        }
        return bounds;
    }

    public Texture getTexture(int index) {
        int i = index % subImages.size();
        return subImages.get(i).load();
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void load() {
        if (loaded)
            return;
        Texture[] imgs = new Texture[subImages.size()];
        for (int i = 0; i < subImages.size(); i++) {
            imgs[i] = subImages.get(i).load();
        }
        loaded = true;
    }

    public class SubImage {
        String name;
        Texture image;
        public Mask mask;

        public SubImage(String name)
            {
                this.name = name;
            }

        protected Texture load() {
            if (image != null)
                return image;

            return image = RuneroGame.tex.getTexture("sprites", name);
        }
    }

    public class Mask {
        public boolean map[][];

        public Mask()
            {
                map = new boolean[width][height];
            }

        public GameSprite getSprite() {
            return GameSprite.this;
        }
    }
}
