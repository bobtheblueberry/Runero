package org.gcreator.runero;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Action.BlockAction;
import org.gcreator.runero.event.Argument;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gml.CodeManager;
import org.gcreator.runero.gml.GmlParser;
import org.gcreator.runero.gml.ReferenceTable;
import org.gcreator.runero.gml.VariableVal;
import org.gcreator.runero.gml.lib.FunctionManager;
import org.gcreator.runero.res.CodeRes;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFont;
import org.gcreator.runero.res.GameInformation;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameResource;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.Tile;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameSprite.Mask;
import org.gcreator.runero.res.GameSprite.MaskShape;
import org.lateralgm.file.gc.StreamDecoder;
import org.lateralgm.resources.library.gc.LibAction;
import org.lateralgm.resources.library.gc.LibAction.LibArgument;
import org.lateralgm.resources.library.gc.LibManager;

public class ResourceLoader {

    RuneroGame game;
    LinkedList<Preloadable> preloadables;

    public ResourceLoader(RuneroGame game)
        {
            this.game = game;
            preloadables = new LinkedList<Preloadable>();
            actionFolder = new File(Runner.GameFolder, "actions/");
        }

    public void loadResources() throws IOException {
        FunctionManager.load();
        System.out.println("Loaded function IDs");

        if (!LibManager.autoLoad())
            throw new IOException("Failed to load Libraries");
        System.out.println("Loaded Libraries");

        loadRooms();
        System.out.println("Loaded room data");
        loadBackgrounds();
        System.out.println("Loaded background data");
        loadObjects();
        if (game.em.hasCollisionEvents)
            Collections.sort(game.em.collision);
        System.out.println("Loaded object data");
        loadSprites();
        System.out.println("Loaded sprite data");
        loadFonts();
        System.out.println("Loaded font data");

        loadGameInfo();
        System.out.println("Loaded Game info");
        loadConstants();
        // Resource name Constants
        addResourceConstants(game.sprites);
        addResourceConstants(game.sounds);
        addResourceConstants(game.backgrounds);
        addResourceConstants(game.paths);
        addResourceConstants(game.scripts);
        addResourceConstants(game.fonts);
        addResourceConstants(game.timelines);
        addResourceConstants(game.objects);
        addResourceConstants(game.rooms);

        System.out.println("Loaded Constants");

        // DEBUG TREE
        /*
                for (GameObject o : game.objects) {
                    System.out.println("-" + o.getName());
                    for (MainEvent me : o.getMainEvents()) {
                        System.out.println(" - " + me.mainEvent);
                        for (Event e : me.events) {
                            System.out.println("  -" + e.type);
                            for (int i = 0; i < e.actions.size(); i++) {
                                Action a = e.actions.get(i);
                                String s = a.lib.id + "";
                                if (a.lib.question)
                                    if (a.ifAction == null)
                                        s = "IF WTFFFFFFFF          ------------------------------------";
                                    else {
                                        String d = "";
                                        if (a.elseAction != null)
                                            d = "  ELSE " + a.elseAction.start + " " + a.elseAction.end + ","
                                                    + a.elseAction.actionEnd;
                                        s = "If " + a.ifAction.start + ":" + a.ifAction.end + "," + a.ifAction.actionEnd + d;
                                    }
                                else if (a.lib.actionKind == Action.ACT_REPEAT)
                                    s = "Repeat " + a.arguments.get(0).val;
                                else if (a.lib.actionKind == Action.ACT_ELSE)
                                    s = "Else";
                                else if (a.lib.actionKind == Action.ACT_BEGIN)
                                    s = "٨";
                                else if (a.lib.actionKind == Action.ACT_END)
                                    s = "٧";
                                else if (a.lib.actionKind == Action.ACT_EXIT)
                                    s = "exit";
                                else if (a.lib.id == org.gcreator.runero.gml.lib.ActionLibrary.COMMENT)
                                    s = "* " + a.arguments.get(0).val;
                                System.out.println(i + ((i < 10) ? " " : "") + "  - " + s);
                            }
                        }
                    }

                }

                System.exit(0);
        */
    }

    private void addResourceConstants(ArrayList<? extends GameResource> res) {
        if (res == null)
            return;
        for (GameResource r : res)
            game.constants.put(r.getName(), new VariableVal(r.getId()));
    }

    private void loadConstants() throws IOException {
        // Load constants
        game.constants = new ReferenceTable<VariableVal>();
        String path = "constants";
        InputStream s;
        try {
            s = new FileInputStream(path);
        } catch (FileNotFoundException exc) {
            s = FunctionManager.class.getResourceAsStream("/" + path);
        }

        if (s == null) {
            System.err.println("Cannot find constants file");
            throw new IOException("Where are constants?");
        }
        BufferedReader r = new BufferedReader(new ISReader(s));
        String line;
        while ((line = r.readLine()) != null) {
            String[] vals = line.split("\\s+", 2);
            double n;
            if (vals[1].startsWith("#")) {
                n = Integer.parseInt(vals[1].substring(1), 16);
            } else {
                n = Double.parseDouble(vals[1]);
            }
            game.constants.put(vals[0], new VariableVal(n));
        }
        r.close();
    }

    private void loadFonts() throws IOException {
        File fntDir = new File(Runner.GameFolder, "fonts/");
        File[] files = fntDir.listFiles(new FileFilter(".dat"));
        game.fonts = new ArrayList<GameFont>(files.length);
        for (File f : files) {
            BufferedReader r = new BufferedReader(new FileReader(f));
            GameFont fnt = new GameFont(r.readLine());
            fnt.setId(Integer.parseInt(r.readLine()));
            fnt.fontName = r.readLine();
            fnt.size = Integer.parseInt(r.readLine());
            fnt.bold = Boolean.parseBoolean(r.readLine());
            fnt.italic = Boolean.parseBoolean(r.readLine());
            fnt.antialias = Integer.parseInt(r.readLine());
            fnt.charset = Integer.parseInt(r.readLine());
            fnt.rangeMin = Integer.parseInt(r.readLine());
            fnt.rangeMax = Integer.parseInt(r.readLine());
            r.close();
            game.fonts.add(fnt);
        }
    }

    private void loadSprites() throws IOException {
        File sprDir = new File(Runner.GameFolder, "sprites/");
        File[] files = sprDir.listFiles(new FileFilter(".dat"));
        game.sprites = new ArrayList<GameSprite>(files.length);
        for (File f : files) {
            StreamDecoder in = new StreamDecoder(f);

            GameSprite s = new GameSprite(in.readStr());
            s.setId(in.read4());
            s.width = in.read4();
            s.height = in.read4();
            int subimgs = in.read4();
            for (int i = 0; i < subimgs; i++) {
                File sf = new File(sprDir, in.readStr());
                s.subImages.add(s.new SubImage(sf));
            }
            s.transparent = in.readBool();
            int shape = in.read();
            if (shape == 0)
                s.shape = MaskShape.RECTANGLE;
            else if (shape == 1)
                s.shape = MaskShape.PRECISE;
            else if (shape == 2)
                s.shape = MaskShape.DISK;
            else if (shape == 3)
                s.shape = MaskShape.DIAMOND;
            else if (shape == 4)
                s.shape = MaskShape.POLYGON;
            s.alpha_tolerance = in.read4();
            s.mask = in.readBool();
            s.smooth = in.readBool();
            s.preload = in.readBool();
            s.x = in.read4();
            s.y = in.read4();
            s.left = in.read4();
            s.right = in.read4();
            s.top = in.read4();
            s.bottom = in.read4();
            loadMask(s, in);

            in.close();
            game.sprites.add(s);
        }
    }

    private void loadMask(GameSprite s, StreamDecoder in) throws IOException {
        int len = in.read4();
        if (len <= 0)
            return;
        int numbers = s.width * s.height;
        for (int i = 0; i < s.subImages.size(); i++) {
            x = y = 0;
            Mask mask = s.new Mask();
            s.subImages.get(i).mask = mask;
            // Load Collision mask
            for (int c = 0; c < len; c++) {
                char d = (char) in.read();
                int k = 0;
                for (int j = 7; j >= 0; j--) {
                    if (c * 8 + k++ >= numbers) {
                        break;
                    }
                    boolean r = ((d >>> j) & 1) == 1;
                    add(r, mask, s.width, s.height);
                }
            }

        }
    }

    int x, y;

    private void add(boolean b, Mask m, int width, int height) {
        m.map[x][y] = b;
        if (x + 1 >= width) {
            x = 0;
            y++;
        } else
            x++;
    }

    private void loadObjects() throws IOException {
        File objDir = new File(Runner.GameFolder, "objects/");
        File[] files = objDir.listFiles(new FileFilter(".dat"));
        game.objects = new ArrayList<GameObject>(files.length);
        for (File f : files) {
            BufferedReader r = new BufferedReader(new FileReader(f));
            GameObject o = new GameObject(r.readLine());
            o.setId(Integer.parseInt(r.readLine()));
            o.spriteId = Integer.parseInt(r.readLine());
            o.solid = Boolean.parseBoolean(r.readLine());
            o.visible = Boolean.parseBoolean(r.readLine());
            o.depth = Integer.parseInt(r.readLine());
            o.persistent = Boolean.parseBoolean(r.readLine());
            o.parentId = Integer.parseInt(r.readLine());
            o.maskId = Integer.parseInt(r.readLine());
            String line;
            while ((line = r.readLine()) != null) {
                String[] evt = line.split(",");
                byte a = Byte.parseByte(evt[0]);
                byte b = Byte.parseByte(evt[1]);
                MainEvent e = o.getMainEvent(a);
                Event ev = new Event(e, b);
                ev.object = o;
                String[] actions = r.readLine().split(",");
                for (String ac : actions) {
                    if (ac.startsWith("#")) {
                        Action act = loadAction(ac.substring(1));
                        ev.addAction(act);
                    }
                }
                indentEvent(ev);
                e.addEvent(ev);
            }
            game.em.addObject(o);
            r.close();
            game.objects.add(o);
        }
    }

    private void loadRooms() throws IOException {
        // First thing is to load rooms
        ArrayList<Integer> rooms = new ArrayList<Integer>();
        ArrayList<String> roomNames = new ArrayList<String>();
        File roomDir = new File(Runner.GameFolder, "rooms/");
        File roomData = new File(roomDir, "rooms.lst");

        StreamDecoder in = new StreamDecoder(roomData);
        int num = in.read4();
        int[] ro = new int[num];
        for (int i = 0; i < num; i++) {
            int n;
            rooms.add(n = in.read4());
            roomNames.add(in.readStr());
            ro[i] = n;
        }
        game.roomOrder = ro;
        in.close();

        game.rooms = new ArrayList<GameRoom>(num);
        for (int i = 0; i < game.roomOrder.length; i++) {
            File rf = new File(roomDir, roomNames.get(i) + ".dat");
            in = new StreamDecoder(rf);
            GameRoom room = new GameRoom(in.readStr());
            room.setId(in.read4());
            room.caption = in.readStr();
            room.setWidth(in.read4());
            room.setHeight(in.read4());
            room.speed = in.read4();
            room.persistent = in.readBool();
            room.background_color = new java.awt.Color(in.read4());
            room.draw_background_color = in.readBool();
            room.creation_code = CodeRes.load(in.readStr());
            int bgs = in.read4();
            room.backgrounds = new GameRoom.Background[bgs];
            for (int j = 0; j < bgs; j++) {
                GameRoom.Background b = new GameRoom.Background();
                b.visible = in.readBool();
                b.foreground = in.readBool();
                b.backgroundId = in.read4();
                b.x = in.read4();
                b.y = in.read4();
                b.tileHoriz = in.readBool();
                b.tileVert = in.readBool();
                b.hSpeed = in.read4();
                b.vSpeed = in.read4();
                b.stretch = in.readBool();
                room.backgrounds[j] = b;
            }
            room.enable_views = in.readBool();
            int views = in.read4();
            room.views = new GameRoom.View[views];
            for (int j = 0; j < views; j++) {
                GameRoom.View v = new GameRoom.View();
                v.visible = in.readBool();
                v.viewX = in.read4();
                v.viewY = in.read4();
                v.viewW = in.read4();
                v.viewH = in.read4();
                v.portX = in.read4();
                v.portY = in.read4();
                v.portW = in.read4();
                v.portH = in.read4();
                v.borderH = in.read4();
                v.borderV = in.read4();
                v.speedH = in.read4();
                v.speedV = in.read4();
                v.objectId = in.read4();

                room.views[j] = v;
            }
            int instances = in.read4();
            room.staticInstances = new ArrayList<GameRoom.StaticInstance>(instances);
            for (int j = 0; j < instances; j++) {
                GameRoom.StaticInstance inst = new GameRoom.StaticInstance();
                inst.x = in.read4();
                inst.y = in.read4();
                inst.objectId = in.read4();
                inst.id = in.read4();
                inst.creationCode = CodeRes.load(in.readStr());
                room.staticInstances.add(inst);
            }
            int tiles = in.read4();
            room.tiles = new GameRoom.Tile[tiles];
            for (int j = 0; j < tiles; j++) {
                Tile t = new Tile();
                t.roomX = in.read4();
                t.roomY = in.read4();
                t.bgX = in.read4();
                t.bgY = in.read4();
                t.width = in.read4();
                t.height = in.read4();
                t.depth = in.read4();
                t.backgroundId = in.read4();
                t.id = in.read4();
                room.tiles[j] = t;
            }
            in.close();
            game.rooms.add(room);
        }
    }

    private void loadBackgrounds() throws IOException {
        File bgDir = new File(Runner.GameFolder, "backgrounds/");
        File[] bgFiles = bgDir.listFiles(new FileFilter(".dat"));
        game.backgrounds = new ArrayList<GameBackground>(bgFiles.length);
        for (File f : bgFiles) {
            BufferedReader r = new BufferedReader(new FileReader(f));
            GameBackground b = new GameBackground(r.readLine());
            b.setId(Integer.parseInt(r.readLine()));
            b.transparent = Boolean.parseBoolean(r.readLine());
            b.smoothEdges = Boolean.parseBoolean(r.readLine());
            b.preload = Boolean.parseBoolean(r.readLine());
            b.useAsTileset = Boolean.parseBoolean(r.readLine());
            b.tileWidth = Integer.parseInt(r.readLine());
            b.tileHeight = Integer.parseInt(r.readLine());
            b.hOffset = Integer.parseInt(r.readLine());
            b.vOffset = Integer.parseInt(r.readLine());
            b.hSep = Integer.parseInt(r.readLine());
            b.vSep = Integer.parseInt(r.readLine());
            String bgImage = r.readLine();
            b.imageFile = new File(bgDir, bgImage);
            r.close();
            game.backgrounds.add(b);
            if (b.preload) {
                preloadables.add(b);
            }
        }
    }

    private int read4(InputStream s) throws IOException {
        int a = s.read();
        int b = s.read();
        int c = s.read();
        int d = s.read();
        return (a | (b << 8) | (c << 16) | (d << 24));
    }

    private void loadGameInfo() throws IOException {
        File settingsFile = new File(Runner.GameFolder, "Game Information.dat");
        GameInformation g = new GameInformation();
        BufferedReader r = new BufferedReader(new FileReader(settingsFile));
        g.backgroundColor = new java.awt.Color(Integer.parseInt(r.readLine()));
        g.caption = r.readLine();
        g.left = Integer.parseInt(r.readLine());
        g.top = Integer.parseInt(r.readLine());
        g.width = Integer.parseInt(r.readLine());
        g.height = Integer.parseInt(r.readLine());
        g.mimicGameWindow = Boolean.parseBoolean(r.readLine());
        g.showBorder = Boolean.parseBoolean(r.readLine());
        g.allowResize = Boolean.parseBoolean(r.readLine());
        g.stayOnTop = Boolean.parseBoolean(r.readLine());
        g.pauseGame = Boolean.parseBoolean(r.readLine());
        r.close();

        File infoFile = new File(Runner.GameFolder, "Game Information.rtf");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(infoFile));
        byte data[] = new byte[read4(in)];
        in.read(data);
        g.text = new String(data, Charset.forName("ISO-8859-1"));
        in.close();
        RuneroGame.game.gameInfo = g;
    }

    File actionFolder;

    private Action loadAction(String file) throws IOException {
        File f = new File(actionFolder, "a_" + file + ".dat");
        StreamDecoder in = new StreamDecoder(f);

        int parentId = in.read4();
        int id = in.read4();
        LibAction a = LibManager.getLibAction(parentId, id);
        // Done of Lib stuff
        // Load Action
        Action act = new Action(a);
        int args = in.read4();
        for (int i = 0; i < args; i++) {
            int kind = in.read();
            Argument arg = new Argument(kind);
            int type = in.read();
            if (type == 0) {
                arg.resVal = in.read4();
                arg.val = "Resource; ID: " + arg.resVal;
            } else if (type == 1) {
                arg.val = in.readStr();
            } else if (type == 2)
                arg.code = CodeRes.load(in.readStr());
            act.arguments.add(arg);
        }
        act.appliesTo = in.read4();
        act.relative = in.readBool();
        act.not = in.readBool();
        in.close();
        lexAction(act, a);
        return act;
    }

    private void lexAction(Action a, LibAction r) {
        int start = 0;
        if (a.lib.actionKind == Action.ACT_VARIABLE) {
            start = 1;
            Argument arg = a.arguments.get(0);// variable
            arg.variableVal = CodeManager.getVariable(arg.val);
        }

        for (int i = start; i < r.libArguments.length; i++) {
            LibArgument la = r.libArguments[i];
            Argument arg = a.arguments.get(i);
            if (arg.val == null) {
                System.out.println("Null arg: " + i + " " + r.name);
                continue;
            }
            boolean expr = false;
            if (la.kind == Argument.ARG_BOOLEAN) {
                arg.boolVal = arg.val.equals("1") ? true : false;
            } else if (la.kind == Argument.ARG_COLOR) {
                arg.colorVal = GmlParser.convertGmColor(Integer.parseInt(arg.val));
            } else if (la.kind == Argument.ARG_MENU) {
                arg.menuVal = Integer.parseInt(arg.val);
            } else if (la.kind == Argument.ARG_BOTH) {
                for (int j = 0; j < arg.val.length(); j++) {
                    char c = arg.val.charAt(j);
                    if (c == ' ')
                        continue;
                    else if (c == '\'' || c == '"') {
                        arg.bothIsExpr = true;
                        expr = true;
                        break;
                    } else {
                        arg.bothIsExpr = false;
                        break;
                    }
                }
            } else if (la.kind == Argument.ARG_EXPRESSION) {
                expr = true;
            }

            if (expr) {
                // System.out.println("Argument for " + r.name + " index " + i + ": " + arg.val + ":");
                arg.exprVal = CodeManager.getArgument(arg.val);
                // System.out.println("E: " + arg.exprVal.debugVal);
            }
        }

    }

    /**
     * Indents actions by blocks, if's, else's, and repeat's
     * 
     * @param e the event to indent
     */
    private void indentEvent(Event e) {
        for (int i = 0; i < e.actions.size(); i++) {
            Action a = e.actions.get(i);
            if (a.lib.question) {
                questionIndent(e, i, a);
                i = a.ifAction.actionEnd;
            } else if (a.lib.actionKind == Action.ACT_REPEAT) {
                a.repeatAction = actionIndent(e, i);
                i = a.repeatAction.actionEnd;
            }
        }
    }

    private void questionIndent(Event e, int index, Action a) {
        a.ifAction = actionIndent(e, index);

        // look for else
        int i = a.ifAction.actionEnd + 1;
        if (i >= e.actions.size()) {
            return;
            // there is no else
        }
        Action actElse = e.actions.get(i);
        if (actElse.lib.actionKind == Action.ACT_ELSE) {
            a.elseAction = actionIndent(e, i);
        }
    }

    /**
     *  for indenting questions, else and repeat action
     */
    private BlockAction actionIndent(Event e, int index) {
        BlockAction qa = new BlockAction();
        if (index + 2 > e.actions.size()) {
            qa.isFake = true;
            return qa;
        }
        Action next = e.actions.get(index + 1);

        if (next.lib.actionKind != Action.ACT_BEGIN) {
            qa.start = index + 1;
            qa.end = index + 1;
            qa.actionEnd = index + 1;
            if (next.lib.question) {
                questionIndent(e, index + 1, next);
                if (next.elseAction != null)
                    qa.end = qa.actionEnd = next.elseAction.actionEnd;
                else
                    qa.end = qa.actionEnd = next.ifAction.actionEnd;

                if (next.ifAction.isBlock)
                    qa.end++;
            } else if (next.lib.actionKind == Action.ACT_REPEAT) {
                next.repeatAction = actionIndent(e, index + 1);
                if (next.repeatAction.isBlock)
                    qa.end = qa.actionEnd = next.repeatAction.actionEnd + 1;
                else
                    qa.end = qa.actionEnd = next.repeatAction.actionEnd;
            }
            return qa;
        }
        qa.start = index + 2;
        qa.isBlock = true;
        for (int i = qa.start; i < e.actions.size(); i++) {
            Action a = e.actions.get(i);

            if (a.lib.question) {
                questionIndent(e, i, a);
                i = a.ifAction.actionEnd;
            } else if (a.lib.actionKind == Action.ACT_REPEAT) {
                a.repeatAction = actionIndent(e, i);
                i = a.repeatAction.actionEnd;
            }
            if (a.lib.actionKind == Action.ACT_END) {
                if (i == index + 2)
                    // empty block
                    qa.isFake = true;

                qa.end = i - 1;
                qa.actionEnd = i;

                break;
            }
        }
        return qa;
    }

    private static class FileFilter implements FilenameFilter {
        String ext;

        public FileFilter(String ext)
            {
                this.ext = ext;
            }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(ext);
        }
    }

    static class ISReader extends Reader {

        InputStream s;

        public ISReader(InputStream s)
            {
                this.s = s;
            }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            byte[] buffer = new byte[cbuf.length];
            int r = s.read(buffer, off, len);
            int i = 0;
            for (byte b : buffer)
                cbuf[i++] = (char) b;
            return r;
        }

        @Override
        public void close() throws IOException {
            s.close();
        }

    }
}
