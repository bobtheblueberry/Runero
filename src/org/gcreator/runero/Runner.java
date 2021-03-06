package org.gcreator.runero;

import java.io.File;
import java.io.IOException;

import org.gcreator.runero.audio.RuneroSound;
import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.gfx.RuneroDisplay;
import org.gcreator.runero.inst.Instance;

public class Runner {

    public static ResourceLoader rl;
    public static boolean error_occurred = false;
    public static String error_last = "";
    public static boolean JAR;
    
    public static Action curAction;
    public static Event curEvent;
    public static Instance curObject;

    public static File GameFolder;

    public static void Error(String message) {
        error_occurred = true;
        error_last = message;
        
        StackTraceElement[] e = new Throwable().getStackTrace();
        String s = curObject + " " +  curEvent + " " + curAction + "\n"; 
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
            this(folder, false);
        }

    public Runner(File folder, boolean jar)
        {
            JAR = jar;
            // Load LWJGL
            try {
                File f;
                if (jar)
                    f = new File("LWJGL/");
                else
                    f = new File("plugins/shared/");
                if (!f.exists())
                    f.mkdirs();

                new LWJGLDownloader(f).checkLWJGL();
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
            RuneroSound.setupAudio();
            game.loadGame();
            RuneroGame.display = new RuneroDisplay();
            RuneroGame.display.start(game);
            // Java Usually crashes here
        }
}
