package org.gcreator.runero;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

    public void saveUrl(File out, String urlString) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(out);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }
}
