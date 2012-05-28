package org.gcreator.runero.res;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.IOUtils;
import org.gcreator.runero.Preloadable;
import org.gcreator.runero.Runner;
import org.gcreator.runero.gfx.TextureLoader;

public class GameSound extends GameResource implements Preloadable {

    public String fileName;
    public String origName;
    public String fileType;
    public SoundKind kind;

    public boolean chorus;
    public boolean echo;
    public boolean flanger;
    public boolean gargle;
    public boolean reverb;
    public double volume;
    public double pan;
    public boolean preload;

    private byte[] data;

    public enum SoundKind {
        NORMAL, BACKGROUND, SPATIAL, MULTIMEDIA
    }

    public GameSound(String name)
        {
            super(name);
        }

    public byte[] getData() {
        if (data == null)
            load();
        return data;
    }
    
    @Override
    public boolean isLoaded() {
        return data != null;
    }

    @Override
    public void load() {
        if (data != null)
            return;
        try {
            InflaterInputStream in;
            String path = "sounds/" + fileName;
            if (Runner.JAR)
                in = new InflaterInputStream(new BufferedInputStream(TextureLoader.class.getResourceAsStream("/res/"
                        + path)));
            else
                in = new InflaterInputStream(new BufferedInputStream(new FileInputStream(Runner.GameFolder.getPath()
                        + File.separator + path)));
            data = IOUtils.toByteArray(in);
            in.close();
        } catch (IOException e) {
            System.err.println("Cannot load sound data " + fileName);
            e.printStackTrace();
        }
    }
}
