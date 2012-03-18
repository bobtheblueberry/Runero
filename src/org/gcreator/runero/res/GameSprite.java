package org.gcreator.runero.res;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;

import org.gcreator.runero.Preloadable;
import org.gcreator.runero.RuneroGame;
import org.newdawn.slick.opengl.Texture;

public class GameSprite extends GameResource implements Preloadable {
    public enum BBMode {
        AUTO, FULL, MANUAL
    }

    public enum MaskShape {
        PRECISE, RECTANGLE, DISK, DIAMOND
    }

    public MaskShape shape;
    public BBMode bbMode;
    public boolean transparent;
    // GM8; Ignored - int alpha;
    // GM8; Ignored - boolean mask;
    public boolean smooth;
    public boolean preload;
    public int x, y;
    public BBMode mode;
    public int left, right, top, bottom;
    public int width, height;
    public ArrayList<SubImage> subImages;
    private boolean loaded = false;
    private Rectangle bounds;

    public GameSprite(String name) {
        super(name);
        subImages = new ArrayList<GameSprite.SubImage>();
    }

    public Rectangle getBounds() {
        if (bounds == null)
            bounds = new Rectangle(Math.abs(right - left), Math.abs(bottom - top));
        return bounds;
    }

    public Texture getSubImage(int index) {
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
        File file;
        Texture image;

        public SubImage(File f) {
            this.file = f;
        }

        protected Texture load() {
            if (image != null)
                return image;

            return image = RuneroGame.tex.getTexture(file);
        }
    }
}
