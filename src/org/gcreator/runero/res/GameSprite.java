package org.gcreator.runero.res;

import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.gcreator.runero.Preloadable;

import com.golden.gamedev.util.ImageUtil;

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
    
    public BufferedImage getSubImage(int index) {
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
        BufferedImage[] imgs = new BufferedImage[subImages.size()];
        for (int i = 0; i < subImages.size(); i++) {
            imgs[i] = subImages.get(i).load();
        }
        loaded = true;
    }
    
    public class SubImage {
        File file;
        BufferedImage image = null;

        public SubImage(File f) {
            this.file = f;
        }
        
        protected BufferedImage load() {
            if (image != null) 
                return image;  
            try {
                int transparency = (transparent) ? Transparency.BITMASK : Transparency.OPAQUE;
                
                return image = ImageUtil.getImage(file.toURI().toURL(), transparency);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
