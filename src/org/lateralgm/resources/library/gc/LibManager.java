/*
 * Copyright (C) 2007, 2008 IsmAvatar <IsmAvatar@gmail.com>
 * Copyright (C) 2006, 2007 Clam <clamisgood@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.resources.library.gc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Argument;
import org.lateralgm.file.gc.GmStreamDecoder;

public final class LibManager {
    private LibManager()
        {
        }

    public static ArrayList<Library> libs = new ArrayList<Library>();
    public static LibAction codeAction;

    public static LibAction getLibAction(int libraryId, int libActionId) {
        for (Library l : libs) {
            if (l.id == libraryId) {
                LibAction act = l.getLibAction(libActionId);
                if (act != null)
                    return act;
            }
        }
        return null;
    }

    /**
     * This is the usual LibManager "entry point".
     * Loads in all libs/lgls in locations specified by preferences.
     */
    public static boolean autoLoad() {
        String path = "org/lateralgm/resources/library/lib";
        File defdir = new File(path);
        File workDir = null;
        try {
            workDir = new File(LibManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        if (!defdir.exists()) 
            defdir = new File(workDir, path);
        if (!defdir.exists())
            defdir = workDir;

        codeAction = null;
        autoLoad(defdir);

        if (codeAction == null)
            codeAction = makeCodeAction();

        return !libs.isEmpty();
    }

    /** Loads in all libs/lgls in a given location (directory or zip file) */
    public static void autoLoad(File loc) {
        Map<String, InputStream> map = getLibs(loc);
        if (map != null)
            loadLibMap(map, loc);
    }

    /**
     * Retrieves all libs/lgls in a given location (directory or zip file).
     * The files are stored in order and already opened for convenience.
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

    public static final String[] EXTS =
        { ".lib", ".lgl" }; //$NON-NLS-1$ //$NON-NLS-2$

    private static boolean passFilter(String fn) {
        for (String ext : EXTS)
            if (fn.endsWith(ext))
                return true;
        return false;
    }

    public static void loadLibMap(Map<String, InputStream> libs, File path) {
        StringBuilder buffer = new StringBuilder();

        for (Map.Entry<String, InputStream> ent : libs.entrySet()) {
            String fn = ent.getKey();
            try {
                loadFile(new GmStreamDecoder(ent.getValue()), fn);

                // print out filename
                if (buffer.length() + fn.length() > 60) {
                    buffer.delete(0, buffer.length() - 1);
                }
                buffer.append(fn).append(' ');
            } catch (LibFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Loads a library file of given fileName of either LIB or LGL format
     * @param filename
     * @return the library
     * @throws LibFormatException
     */
    public static Library loadFile(String filename) throws LibFormatException {
        try {
            return loadFile(new GmStreamDecoder(filename), filename);
        } catch (FileNotFoundException e) {
            throw new LibFormatException("LibManager.ERROR_NOTFOUND " + filename);
        }
    }

    /**
     * Loads a library file of given fileName of either LIB or LGL format
     * @param in
     * @param filename for error reporting
     * @return the library
     * @throws LibFormatException
     */
    public static Library loadFile(GmStreamDecoder in, String filename) throws LibFormatException {
        Library lib = null;
        try {
            int header = in.read3();
            if (header == (('L' << 16) | ('G' << 8) | 'L'))
                lib = loadLgl(in);
            else if (header == 500 || header == 520)
                lib = loadLib(in);
            else
                throw new LibFormatException("LibManager.ERROR_INVALIDFILE " + filename);
            libs.add(lib);
        } catch (IOException ex) {
            throw new LibFormatException("LibManager.ERROR_READING " + filename + ": " + ex.getMessage());
        } catch (LibFormatException ex) {
            throw new LibFormatException(String.format(ex.getMessage(), filename));
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                throw new LibFormatException("LibManager.ERROR_CLOSEFAILED");
            }
        }
        return lib;
    }

    /**
     * Workhorse for constructing a library out of given StreamDecoder of LIB format
     * @param in
     * @return the library (not yet added to the libs list)
     * @throws LibFormatException
     * @throws IOException
     */
    public static Library loadLib(GmStreamDecoder in) throws LibFormatException, IOException {
        if (in.read() != 0)
            throw new LibFormatException("LibManager.ERROR_INVALIDFILE");
        Library lib = new Library();
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
                throw new LibFormatException("LibManager.ERROR_INVALIDACTION" + j + ": " + ver);
            }

            LibAction act = lib.addLibAction();
            act.parent = lib;
            act.name = in.readStr();
            act.id = in.read4();

            byte[] data = new byte[in.read4()];
            in.read(data);// ignore image data

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
            act.libArguments = new LibAction.LibArgument[in.read4()];
            int args = in.read4();
            for (int k = 0; k < args; k++) {
                if (k < act.libArguments.length) {
                    LibAction.LibArgument arg = new LibAction.LibArgument();
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
     * Workhorse for constructing a library out of given StreamDecoder of LGL format
     * @param in
     * @return the library (not yet added to the libs list)
     * @throws LibFormatException
     * @throws IOException
     */
    public static Library loadLgl(GmStreamDecoder in) throws LibFormatException, IOException {
        if (in.read2() != 160) {
            throw new LibFormatException("LibManager.ERROR_INVALIDFILE");
        }
        Library lib = new Library();
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
                throw new LibFormatException("LibManager.ERROR_INVALIDACTION " + j);
            LibAction act = lib.addLibAction();
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
            act.libArguments = new LibAction.LibArgument[in.read()];
            for (int k = 0; k < act.libArguments.length; k++) {
                LibAction.LibArgument arg = new LibAction.LibArgument();
                arg.caption = in.readStr1();
                arg.kind = (byte) in.read();
                arg.defaultVal = in.readStr1();
                arg.menu = in.readStr1();
                act.libArguments[k] = arg;
            }

            if (act.actionKind == Action.ACT_CODE && act.execType == Action.EXEC_CODE
                    && act.interfaceKind == LibAction.INTERFACE_CODE)
                codeAction = act;
        }
        /* removed image code */
        return lib;
    }

    private static LibAction makeCodeAction() {
        LibAction act = new LibAction();
        act.name = "Code";
        act.description = "Execute a piece of code";
        act.listText = "Execute a piece of code";
        act.hintText = "Execute code:##@0";
        act.canApplyTo = true;
        act.execType = Action.EXEC_CODE;
        act.actionKind = Action.ACT_CODE;
        act.interfaceKind = LibAction.INTERFACE_CODE;
        act.libArguments = new LibAction.LibArgument[1];

        act.libArguments[0] = new LibAction.LibArgument();
        act.libArguments[0].kind = Argument.ARG_STRING;

        return act;
    }

    static boolean mask(int bits, int bit) {
        return (bits & bit) == bit;
    }
}
