package org.gcreator.runero;

import java.awt.Dimension;
import java.io.IOException;

import com.golden.gamedev.GameLoader;

public class Runner {

    public static ResourceLoader rl;
    public static GameLoader gl;

    public Runner() {
        RuneroGame r = new RuneroGame();
        // Load Resources
        try {
            rl = new ResourceLoader(r);
            rl.loadResources();
        } catch (IOException e) {
            System.err.println("ERROR LOADING GAME!");
            e.printStackTrace();
        }

        gl = new GameLoader();
        boolean fullscreen = false;
        gl.setup(r, new Dimension(640, 480), fullscreen);
        r.loadGame();
        gl.start();
    }
}
