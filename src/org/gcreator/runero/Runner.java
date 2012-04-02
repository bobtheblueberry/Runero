package org.gcreator.runero;

import java.io.IOException;

import org.gcreator.runero.gfx.RuneroDisplay;

public class Runner {

    public static ResourceLoader rl;

    public static void Error(String message) {
        new ErrorDialog(null, "Runtime Error", message, (String)null);
    }

    public static void Error(String message, String debugInfo) {
        new ErrorDialog(null, "Runtime Error", message, debugInfo);
    }
    
    public static void Error(String message, Throwable error) {
        new ErrorDialog(null, "Runtime Error", message, error);
    }

    public Runner() {
        RuneroGame game = new RuneroGame();
        // Load Resources
        try {
            rl = new ResourceLoader(game);
            rl.loadResources();
        } catch (IOException e) {
            System.err.println("ERROR LOADING GAME!");
            e.printStackTrace();
        }
        game.loadGame();
        RuneroGame.display = new RuneroDisplay();
        RuneroGame.display.start(game);
        System.exit(0);
        // Java Usually crashes here
    }
}
