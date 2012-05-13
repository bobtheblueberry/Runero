package org.gcreator.runero;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;

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
import org.gcreator.runero.res.GamePath;
import org.gcreator.runero.res.GameResource;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.Tile;
import org.gcreator.runero.res.GameScript;
import org.gcreator.runero.res.GameSettings;
import org.gcreator.runero.res.GameSound;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameSprite.Mask;
import org.gcreator.runero.res.GameSprite.MaskShape;
import org.gcreator.runero.res.GameTimeline;
import org.lateralgm.file.gc.StreamDecoder;
import org.lateralgm.resources.library.gc.LibAction;
import org.lateralgm.resources.library.gc.LibAction.LibArgument;
import org.lateralgm.resources.library.gc.LibManager;

public class ResourceLoader {

    RuneroGame game;
    ArrayList<Preloadable> preloadables;
    File roomDir;
    File fontDir;
    File sprDir;
    File scrDir;
    File objDir;
    File sndDir;
    File bgDir;
    File pathDir;
    File tlDir;
    int rooms, fonts, sprites, scripts, objects, backgrounds, paths, timelines, sounds;

    private static final byte SPRITE = 1;
    private static final byte BACKGROUND = 2;
    private static final byte SOUND = 3;
    private static final byte PATH = 4;
    private static final byte SCRIPT = 5;
    private static final byte FONT = 6;
    private static final byte TIMELINE = 7;
    private static final byte OBJECT = 8;
    private static final byte ROOM = 9;
    private static final byte GAME_INFO = 10;
    private static final byte SETTINGS = 11;

    public ResourceLoader(RuneroGame game)
        {
            this.game = game;
            preloadables = new ArrayList<Preloadable>();
            roomDir = new File(Runner.GameFolder, "rooms/");
            fontDir = new File(Runner.GameFolder, "fonts/");
            sprDir = new File(Runner.GameFolder, "sprites/");
            scrDir = new File(Runner.GameFolder, "scripts/");
            objDir = new File(Runner.GameFolder, "objects/");
            sndDir = new File(Runner.GameFolder, "sounds/");
            bgDir = new File(Runner.GameFolder, "backgrounds/");
            pathDir = new File(Runner.GameFolder, "paths/");
            tlDir = new File(Runner.GameFolder, "timelines/");
        }

    public void loadResources() throws IOException {
        FunctionManager.load();
        if (!LibManager.autoLoad())
            throw new IOException("Failed to load Libraries");

        File res = new File(Runner.GameFolder, "resources.dat");
        if (!res.exists())
            throw new IOException("No resource data file! Did you export ?");

        StreamDecoder in = new StreamDecoder(res);
        sprites = in.read4();
        backgrounds = in.read4();
        sounds = in.read4();
        paths = in.read4();
        scripts = in.read4();
        fonts = in.read4();
        timelines = in.read4();
        objects = in.read4();
        rooms = in.read4();
        game.sprites = new ArrayList<GameSprite>(sprites);
        game.backgrounds = new ArrayList<GameBackground>(backgrounds);
        game.fonts = new ArrayList<GameFont>(fonts);
        game.rooms = new ArrayList<GameRoom>(rooms);
        game.objects = new ArrayList<GameObject>(objects);
        game.sounds = new ArrayList<GameSound>(sounds);
        game.scripts = new ArrayList<GameScript>(scripts);
        game.timelines = new ArrayList<GameTimeline>(timelines);
        game.paths = new ArrayList<GamePath>(paths);
        game.roomOrder = new int[rooms];

        int b;
        while ((b = in.read()) != -1) {
            loadRes(b, in.readStr());
        }

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

        if (game.em.hasCollisionEvents)
            Collections.sort(game.em.collision);

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

    private void loadRes(int val, String name) throws IOException {
        switch (val) {
            case SPRITE:
                loadSprite(name);
                break;
            case BACKGROUND:
                loadBackground(name);
                break;
            case SOUND:
                // loadSound(name);
                break;
            case PATH:
                // loadPath(name);
                break;
            case SCRIPT:
                // loadScript(name);
                break;
            case FONT:
                loadFont(name);
                break;
            case TIMELINE:
                // loadTimeline(name);
                break;
            case OBJECT:
                loadObject(name);
                break;
            case ROOM:
                loadRoom(name);
                break;
            case GAME_INFO:
                loadGameInfo(name);
                break;
            case SETTINGS:
                loadSettings(name);
                break;
        }
    }

    private void loadSettings(String name) {
        File f = new File(Runner.GameFolder, name);
        GameSettings s = new GameSettings();
        RuneroGame.settings = s;
        try {
            StreamDecoder in = new StreamDecoder(f);
            s.gameId = in.read4();
            s.startFullscreen = in.readBool();
            s.interpolate = in.readBool();
            s.dontDrawBorder = in.readBool();
            s.displayCursor = in.readBool();
            s.scaling = in.read4();
            s.allowWindowResize = in.readBool();
            s.alwaysOnTop = in.readBool();
            s.colorOutsideRoom = in.read4();
            s.setResolution = in.readBool();
            s.colorDepth = (byte) in.read();
            s.resolution = (byte) in.read();
            s.frequency = (byte) in.read();
            s.dontShowButtons = in.readBool();
            s.useSynchronization = in.readBool();
            s.disableScreensavers = in.readBool();
            s.letF4SwitchFullscreen = in.readBool();
            s.letF1ShowGameInfo = in.readBool();
            s.letEscEndGame = in.readBool();
            s.letF5SaveF6Load = in.readBool();
            s.letF9Screenshot = in.readBool();
            s.treatCloseAsEscape = in.readBool();
            s.gamePriority = (byte) in.read();
            s.freezeOnLoseFocus = in.readBool();
            s.loadBarMode = (byte) in.read();
            s.showCustomLoadImage = in.readBool();
            s.imagePartiallyTransparent = in.readBool();
            s.loadImageAlpha = in.read4();
            s.scaleProgressBar = in.readBool();
            s.displayErrors = in.readBool();
            s.writeToLog = in.readBool();
            s.abortOnError = in.readBool();
            s.treatUninitializedAs0 = in.readBool();
            s.author = in.readStr();
            s.version = in.readStr();
            s.lastChanged = in.readD();
            s.information = in.readStr();
            s.includeFolder = in.read4();
            s.overwriteExisting = in.readBool();
            s.removeAtGameEnd = in.readBool();
            s.versionMajor = in.read4();
            s.versionMinor = in.read4();
            s.versionRelease = in.read4();
            s.versionBuild = in.read4();
            s.company = in.readStr();
            s.product = in.readStr();
            s.copyright = in.readStr();
            s.description = in.readStr();
            in.close();
        } catch (IOException e) {
            System.out.println("Cannot load settings!");
            e.printStackTrace();
        }
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

    private void loadFont(String name) throws IOException {
        File f = new File(fontDir, name);
        StreamDecoder in = new StreamDecoder(f);
        GameFont fnt = new GameFont(in.readStr());
        fnt.setId(in.read4());
        fnt.fontName = in.readStr();
        fnt.size = in.read4();
        fnt.bold = in.readBool();
        fnt.italic = in.readBool();
        fnt.antialias = in.read4();
        fnt.charset = in.read4();
        fnt.rangeMin = in.read4();
        fnt.rangeMax = in.read4();
        in.close();
        game.fonts.add(fnt);
    }

    private void loadSprite(String name) throws IOException {
        File f = new File(sprDir, name);
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
        if (s.preload) {
            preloadables.add(s);
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

    private void loadObject(String name) throws IOException {
        File f = new File(objDir, name);
        StreamDecoder in = new StreamDecoder(f);
        GameObject o = new GameObject(in.readStr());
        o.setId(in.read4());
        o.spriteId = in.read4();
        o.solid = in.readBool();
        o.visible = in.readBool();
        o.persistent = in.readBool();
        o.depth = in.read4();
        o.parentId = in.read4();
        o.maskId = in.read4();

        int numMainEvents = in.read4();
        for (int i = 0; i < numMainEvents; i++) {
            int events = in.read4();
            for (int j = 0; j < events; j++) {
                MainEvent e = o.getMainEvent((byte) in.read());
                Event ev = new Event(e, in.read4());
                ev.object = o;
                int actn = in.read4();
                for (int a = 0; a < actn; a++)
                    ev.addAction(loadAction(in));
                indentEvent(ev);
                e.addEvent(ev);
            }
        }

        game.em.addObject(o);
        in.close();
        game.objects.add(o);
    }

    int roomi;

    private void loadRoom(String name) throws IOException {
        File rf = new File(roomDir, name);
        StreamDecoder in = new StreamDecoder(rf);
        GameRoom room = new GameRoom(in.readStr());
        room.setId(in.read4());
        room.orderIndex = roomi;
        game.roomOrder[roomi++] = room.getId();
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

    private void loadBackground(String name) throws IOException {
        File f = new File(bgDir, name);
        StreamDecoder in = new StreamDecoder(f);
        GameBackground b = new GameBackground(in.readStr());
        b.setId(in.read4());
        b.transparent = in.readBool();
        b.smoothEdges = in.readBool();
        b.preload = in.readBool();
        b.useAsTileset = in.readBool();
        b.tileWidth = in.read4();
        b.tileHeight = in.read4();
        b.hOffset = in.read4();
        b.vOffset = in.read4();
        b.hSep = in.read4();
        b.vSep = in.read4();
        String bgImage = in.readStr();
        b.imageFile = new File(bgDir, bgImage);
        in.close();
        game.backgrounds.add(b);
        if (b.preload) {
            preloadables.add(b);
        }
    }

    private void loadGameInfo(String name) throws IOException {
        File f = new File(Runner.GameFolder, name);
        StreamDecoder in = new StreamDecoder(f);
        GameInformation g = new GameInformation();
        g.text = in.readStr();
        g.backgroundColor = new java.awt.Color(in.read4());
        g.caption = in.readStr();
        g.left = in.read4();
        g.top = in.read4();
        g.width = in.read4();
        g.height = in.read4();
        g.mimicGameWindow = in.readBool();
        g.showBorder = in.readBool();
        g.allowResize = in.readBool();
        g.stayOnTop = in.readBool();
        g.pauseGame = in.readBool();
        in.close();
        RuneroGame.game.gameInfo = g;
    }

    File actionFolder;

    private Action loadAction(StreamDecoder in) throws IOException {

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
