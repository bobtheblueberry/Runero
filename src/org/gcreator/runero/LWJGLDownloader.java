package org.gcreator.runero;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LWJGLDownloader {

    static File folder;
    static String[] jars = new String[]
        { "lwjgl.jar", "slick-util.jar", "lwjgl_util.jar" };
    static String[] linux = new String[]
        { "libjinput-linux.so", "libjinput-linux64.so", "liblwjgl.so", "liblwjgl64.so", "libopenal.so",
                "libopenal64.so" };
    static String[] windows = new String[]
        { "OpenAL32.dll", "OpenAL64.dll", "jinput-dx8.dll", "jinput-dx8_64.dll", "jinput-raw.dll", "jinput-raw_64.dll",
                "lwjgl.dll", "lwjgl64.dll" };
    static String[] solaris = new String[]
        { "liblwjgl.so", "liblwjgl64.so", "libopenal.so", "libopenal64.so" };

    static String[] mac = new String[]
        { "libjinput-osx.jnilib", "liblwjgl.jnilib", "openal.dylib" };

    static enum OS {
        WINDOWS, LINUX, MAC, SOLARIS
    }

    static OS osType;

    public LWJGLDownloader()
        {
            folder = new File("plugins/shared/");
            if (!folder.exists())
                folder.mkdir();
            
        }

    public void checkLWJGL() throws IOException {
        if (!folder.exists())
            return;
        URL[] files = new URL[jars.length];
        int i = 0;
        for (String f : jars) {
            File file = new File(folder, f);
            files[i++] = file.toURI().toURL();
            if (!file.exists()) {
                System.out.println("Downloading " + f);
                saveUrl(file, "http://bobtheblueberry.com/lwjgl/jar/" + f);
            }
        }
        new URLClassLoader(files);
        checkNative();
        System.setProperty("org.lwjgl.librarypath", folder.getAbsolutePath());
    }

    private void checkNative() throws MalformedURLException, IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows"))
            osType = OS.WINDOWS;
        else if (os.contains("linux"))
            osType = OS.LINUX;
        else if (os.contains("mac"))
            osType = OS.MAC;
        else if (os.contains("solaris"))
            osType = OS.SOLARIS;
        else {
            System.err.println("Unknown OS " + os);
            System.exit(0);
        }

        if (osType == OS.WINDOWS) {
            for (String f : windows) {
                File file = new File(folder, f);
                if (!file.exists()) {
                    System.out.println("Downloading " + f);
                    saveUrl(file, "http://bobtheblueberry.com/lwjgl/native/windows/" + f);
                }
            }
        } else if (osType == OS.LINUX) {
            for (String f : linux) {
                File file = new File(folder, f);
                if (!file.exists()) {
                    System.out.println("Downloading " + f);
                    saveUrl(file, "http://bobtheblueberry.com/lwjgl/native/linux/" + f);
                }
            }

        } else if (osType == OS.MAC) {
            for (String f : mac) {
                File file = new File(folder, f);
                if (!file.exists()) {
                    System.out.println("Downloading " + f);
                    saveUrl(file, "http://bobtheblueberry.com/lwjgl/native/macosx/" + f);
                }
            }
        } else if (osType == OS.SOLARIS) {
            for (String f : solaris) {
                File file = new File(folder, f);
                if (!file.exists()) {
                    System.out.println("Downloading " + f);
                    saveUrl(file, "http://bobtheblueberry.com/lwjgl/native/solaris/" + f);
                }
            }
        }
    }

    private void saveUrl(File filename, String urlString) throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

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
