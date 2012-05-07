package org.gcreator.runero;

import java.io.File;
import java.io.IOException;

import org.gcreator.runero.gfx.RuneroDisplay;

public class Runner {

    public static ResourceLoader rl;
    public static boolean error_occurred = false;
    public static String error_last = "";

    public static File GameFolder;

    public static void Error(final String message) {
        error_occurred = true;
        error_last = message;

        StackTraceElement[] e = new Throwable().getStackTrace();
        String s = "";
        for (StackTraceElement el : e)
            s += el + "\n";
        System.err.println(message);
        System.err.println(s);
        new ErrorDialog(null, "Runtime Error", message, s);
    }

    public static void Error(String message, String debugInfo) {
        error_occurred = true;
        error_last = message;
        new ErrorDialog(null, "Runtime Error", message, debugInfo);
    }

    public static void Error(String message, Throwable error) {
        error_occurred = true;
        error_last = message;
        new ErrorDialog(null, "Runtime Error", message, error);
    }

    public Runner(File folder)
        {
            // Load LWJGL
            try {
                new LWJGLDownloader().checkLWJGL();
            } catch (IOException e) {
                System.err.println("Error download LWJGL");
                e.printStackTrace();
                return;
            }

            GameFolder = folder;

            RuneroGame game = new RuneroGame();
            // Load Resources
            try {
                rl = new ResourceLoader(game);
                rl.loadResources();
            } catch (IOException e) {
                System.err.println("ERROR LOADING GAME!");
                e.printStackTrace();
                return;
            }
            game.loadGame();
            RuneroGame.display = new RuneroDisplay();
            RuneroGame.display.start(game);
            // Java Usually crashes here
        }
}
