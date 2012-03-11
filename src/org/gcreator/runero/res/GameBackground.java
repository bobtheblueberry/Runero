package org.gcreator.runero.res;

import java.io.File;

import org.gcreator.runero.Preloadable;
import org.gcreator.runero.RuneroGame;

import com.golden.gamedev.object.background.ImageBackground;

public class GameBackground extends GameResource implements Preloadable {
    
    public boolean transparent;
    public boolean smoothEdges;
    public boolean preload;
    public boolean useAsTileset;
    public int tileWidth;
    public int tileHeight;
    public int hOffset;
    public int vOffset;
    public int hSep;
    public int vSep;
    
    public File imageFile;
    
    private ImageBackground bg = null;
    
    public GameBackground(String name) {
        super(name);
    }
    
    public void load() {
        if (bg != null)
            return;
        bg = new ImageBackground(RuneroGame.game.getImage(imageFile.getPath()));
    }

    public ImageBackground getBackground() {
        if (bg == null) {
            load();
        }
        return bg;
    }

    @Override
    public boolean isLoaded() {
        return bg != null;
    }
}
