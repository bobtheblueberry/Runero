package org.gcreator.runero;

import java.io.File;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Look for resources

        File f = new File("/home/serge/Develop/Runero/RuneroGame");
        // If not resourses, show a dialog and exit
        if (!f.exists()) {
            System.err.println("Cannot find game data");
            return;
        }

        // Start the game

        new Runner(f);
    }
}
