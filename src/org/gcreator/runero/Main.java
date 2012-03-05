package org.gcreator.runero;

import org.lateralgm.resources.library.RLibManager;


public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        // Look for resources

        // If not resourses, show a dialog and exit
        
        // Load Libraries
        RLibManager.autoLoad();
        System.out.println("Loaded Libraries");
        
        // Start the game

        new Runner();
    }

}
