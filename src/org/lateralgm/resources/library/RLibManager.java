/*
 * Copyright (C) 2007, 2008 IsmAvatar <IsmAvatar@gmail.com>
 * Copyright (C) 2006, 2007 Clam <clamisgood@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.resources.library;

import static org.lateralgm.file.RGmStreamDecoder.mask;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Argument;
import org.lateralgm.file.RGmStreamDecoder;

public final class RLibManager {
    private RLibManager() {
    }

    public static ArrayList<RLibrary> libs = new ArrayList<RLibrary>();
    public static RLibAction codeAction;

    public static RLibAction getLibAction(int libraryId, int libActionId) {
        for (RLibrary l : libs) {
            if (l.id == libraryId) {
                RLibAction act = l.getLibAction(libActionId);
                if (act != null)
                    return act;
            }
        }
        return null;
    }

    /**
     * This is the usual LibManager "entry point". Loads in all libs/lgls in
     * locations specified by preferences.
     */
    public static void autoLoad() {
        autoLoad(new File("src/org/lateralgm/resources/library/lib/"));

        if (codeAction == null)
            codeAction = makeCodeAction();
    }

    /** Loads in all libs/lgls in a given location (directory or zip file) */
    public static void autoLoad(File loc) {
        Map<String, InputStream> map = getLibs(loc);
        if (map != null)
            loadLibMap(map, loc);
    }

    /**
     * Retrieves all libs/lgls in a given location (directory or zip file). The
     * files are stored in order and already opened for convenience.
     */
    public static TreeMap<String, InputStream> getLibs(File loc) {
        if (loc.exists()) {
            try {
                if (loc.isDirectory()) 
                    return getDirLibs(loc);
                if (!passFilter(loc.getName()))
                    return getZipLibs(new ZipFile(loc));
                

                // loc is a lib/lgl already...
                TreeMap<String, InputStream> map = new TreeMap<String, InputStream>();
                map.put(loc.getName(), new FileInputStream(loc));
                return map;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static TreeMap<String, InputStream> getZipLibs(ZipFile zip) throws IOException {
        TreeMap<String, InputStream> map = new TreeMap<String, InputStream>();
        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry ent = entries.nextElement();
            String en = ent.getName();
            if (passFilter(en))
                map.put(en.substring(en.lastIndexOf('/') + 1), zip.getInputStream(ent));
        }
        return map;
    }

    public static TreeMap<String, InputStream> getDirLibs(File dir) throws IOException {
        TreeMap<String, InputStream> map = new TreeMap<String, InputStream>();
        File[] fl = dir.listFiles();
        for (File f : fl) {
            String en = f.getName();
            if (passFilter(en))
                map.put(en, new FileInputStream(f));
        }
        return map;
    }

    public static final String[] EXTS = { ".lib", ".lgl" }; //$NON-NLS-1$ //$NON-NLS-2$

    private static boolean passFilter(String fn) {
        for (String ext : EXTS)
            if (fn.endsWith(ext))
                return true;
        return false;
    }

    public static void loadLibMap(Map<String, InputStream> libs, File path) {
        ArrayList<String> exceptions = new ArrayList<String>();
        if (libs.size() > 0)
            System.out.println("LibManager.LOADINGN " + path.getPath());
        StringBuilder buffer = new StringBuilder();

        for (Map.Entry<String, InputStream> ent : libs.entrySet()) {
            String fn = ent.getKey();
            try {
                loadFile(new RGmStreamDecoder(ent.getValue()), fn);

                // print out filename
                if (buffer.length() + fn.length() > 60) {
                    System.out.println(buffer);
                    buffer.delete(0, buffer.length() - 1);
                }
                buffer.append(fn).append(' ');
            } catch (RLibFormatException ex) {
                exceptions.add(fn + ": " + ex.getMessage());
            }
        }
        System.out.println(buffer);
        for (String s : exceptions)
            System.out.println(s);
    }

    /**
     * Loads a library file of given fileName of either LIB or LGL format
     * 
     * @param filename
     * @return the library
     * @throws RLibFormatException
     */
    public static RLibrary loadFile(String filename) throws RLibFormatException {
        try {
            return loadFile(new RGmStreamDecoder(filename), filename);
        } catch (FileNotFoundException e) {
            throw new RLibFormatException("LibManager.ERROR_NOTFOUND");
        }
    }

    /**
     * Loads a library file of given fileName of either LIB or LGL format
     * 
     * @param in
     * @param filename
     *            for error reporting
     * @return the library
     * @throws RLibFormatException
     */
    public static RLibrary loadFile(RGmStreamDecoder in, String filename) throws RLibFormatException {
        RLibrary lib = null;
        try {
            int header = in.read3();
            if (header == (('L' << 16) | ('G' << 8) | 'L'))
                lib = loadLgl(in);
            else if (header == 500 || header == 520)
                lib = loadLib(in);
            else
                throw new RLibFormatException("LibManager.ERROR_INVALIDFILE");
            libs.add(lib);
        } catch (IOException ex) {
            throw new RLibFormatException("LibManager.ERROR_READING");
        } catch (RLibFormatException ex) {
            throw new RLibFormatException(String.format(ex.getMessage(), filename));
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                String msg = "LibManager.ERROR_CLOSEFAILED"; //$NON-NLS-1$
                throw new RLibFormatException(msg);
            }
        }
        return lib;
    }

    /**
     * Workhorse for constructing a library out of given StreamDecoder of LIB
     * format
     * 
     * @param in
     * @return the library (not yet added to the libs list)
     * @throws RLibFormatException
     * @throws IOException
     */
    public static RLibrary loadLib(RGmStreamDecoder in) throws RLibFormatException, IOException {
        if (in.read() != 0)
            throw new RLibFormatException("LibManager.ERROR_INVALIDFILE");
        RLibrary lib = new RLibrary();
        lib.tabCaption = in.readStr();
        lib.id = in.read4();
        in.skip(in.read4());
        in.skip(4);
        in.skip(8);
        in.skip(in.read4());
        in.skip(in.read4());
        lib.advanced = in.readBool();
        in.skip(4); // no of actions/official lib identifier thingy
        int acts = in.read4();
        for (int j = 0; j < acts; j++) {
            int ver = in.read4();
            if (ver != 500 && ver != 520) {
                throw new RLibFormatException("LibManager.ERROR_INVALIDACTION");
            }

            RLibAction act = lib.addLibAction();
            act.parent = lib;
            act.name = in.readStr();
            act.id = in.read4();

            byte[] data = new byte[in.read4()];
            in.read(data);
            //act.actImage = ImageIO.read(new ByteArrayInputStream(data));

            act.hidden = in.readBool();
            act.advanced = in.readBool();
            if (ver == 520)
                act.registeredOnly = in.readBool();
            act.description = in.readStr();
            act.listText = in.readStr();
            act.hintText = in.readStr();
            act.actionKind = (byte) in.read4();
            act.interfaceKind = (byte) in.read4();
            act.question = in.readBool();
            act.canApplyTo = in.readBool();
            act.allowRelative = in.readBool();
            act.libArguments = new RLibAction.LibArgument[in.read4()];
            int args = in.read4();
            for (int k = 0; k < args; k++) {
                if (k < act.libArguments.length) {
                    RLibAction.LibArgument arg = new RLibAction.LibArgument();
                    arg.caption = in.readStr();
                    arg.kind = (byte) in.read4();
                    arg.defaultVal = in.readStr();
                    arg.menu = in.readStr();
                    act.libArguments[k] = arg;
                } else {
                    in.skip(in.read4());
                    in.skip(4);
                    in.skip(in.read4());
                    in.skip(in.read4());
                }
            }
            act.execType = (byte) in.read4();
            if (act.execType == Action.EXEC_FUNCTION)
                act.execInfo = in.readStr();
            else
                in.skip(in.read4());
            if (act.execType == Action.EXEC_CODE)
                act.execInfo = in.readStr();
            else
                in.skip(in.read4());
        }
        return lib;
    }

    /**
     * Workhorse for constructing a library out of given StreamDecoder of LGL
     * format
     * 
     * @param in
     * @return the library (not yet added to the libs list)
     * @throws RLibFormatException
     * @throws IOException
     */
    public static RLibrary loadLgl(RGmStreamDecoder in) throws RLibFormatException, IOException {
        if (in.read2() != 160) {
            String invalidFile = "LibManager.ERROR_INVALIDFILE"; //$NON-NLS-1$
            throw new RLibFormatException(invalidFile);
        }
        RLibrary lib = new RLibrary();
        lib.id = in.read3();
        lib.tabCaption = in.readStr1();
        in.skip(in.read());
        in.skip(4);
        in.skip(8);
        in.skip(in.read4());
        in.skip(in.read4());
        int acts = in.read();
        lib.advanced = mask(acts, 128);
        acts &= 127;
        for (int j = 0; j < acts; j++) {
            if (in.read2() != 160)
                throw new RLibFormatException("LibManager.ERROR_INVALIDACTION");
            RLibAction act = lib.addLibAction();
            act.parent = lib;
            act.id = in.read2();
            act.name = in.readStr1();
            act.description = in.readStr1();
            act.listText = in.readStr1();
            act.hintText = in.readStr1();
            int tags = in.read();
            act.hidden = mask(tags, 128);
            act.advanced = mask(tags, 64);
            act.registeredOnly = mask(tags, 32);
            act.question = mask(tags, 16);
            act.canApplyTo = mask(tags, 8);
            act.allowRelative = mask(tags, 4);
            act.execType = (byte) (tags & 3);
            act.execInfo = in.readStr();
            tags = in.read();
            act.actionKind = (byte) (tags >> 4);
            act.interfaceKind = (byte) (tags & 15);
            act.libArguments = new RLibAction.LibArgument[in.read()];
            for (int k = 0; k < act.libArguments.length; k++) {
                RLibAction.LibArgument arg = new RLibAction.LibArgument();
                arg.caption = in.readStr1();
                arg.kind = (byte) in.read();
                arg.defaultVal = in.readStr1();
                arg.menu = in.readStr1();
                act.libArguments[k] = arg;
            }

            if (act.actionKind == Action.ACT_CODE && act.execType == Action.EXEC_CODE
                    && act.interfaceKind == RLibAction.INTERFACE_CODE)
                codeAction = act;
        }
        /*
        BufferedImage icons = ImageIO.read(in.getInputStream());
        int i = 0;
        int cc = icons.getWidth() / 24;
        for (RLibAction a : lib.libActions) {
            if (a.actionKind < 8) {
                a.actImage = icons.getSubimage(24 * (i % cc), 24 * (i / cc), 24, 24);
                i++;
            }
        }*/
        return lib;
    }

    private static RLibAction makeCodeAction() {
        RLibAction act = new RLibAction();
        act.name = "Code";
        act.description = "Execute a piece of code";
        act.listText = "Execute a piece of code";
        act.hintText = "Execute code:##@0";
        act.canApplyTo = true;
        act.execType = Action.EXEC_CODE;
        act.actionKind = Action.ACT_CODE;
        act.interfaceKind = RLibAction.INTERFACE_CODE;
        act.libArguments = new RLibAction.LibArgument[1];

        act.libArguments[0] = new RLibAction.LibArgument();
        act.libArguments[0].kind = Argument.ARG_STRING;

        return act;
    }
}
