package org.gcreator.runero;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;

import org.gcreator.runero.event.Action;
import org.gcreator.runero.event.Action.BlockAction;
import org.gcreator.runero.event.Argument;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.res.Code;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFontRes;
import org.gcreator.runero.res.GameInformation;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.Tile;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameSprite.BBMode;
import org.gcreator.runero.res.GameSprite.MaskShape;
import org.lateralgm.resources.library.RLibAction;
import org.lateralgm.resources.library.RLibManager;

public class ResourceLoader {

    RuneroGame game;
    LinkedList<Preloadable> preloadables;

    public ResourceLoader(RuneroGame game) {
        this.game = game;
        preloadables = new LinkedList<Preloadable>();
        actionFolder = new File(game.GameFolder, "actions/");
    }

    public void loadResources() throws IOException {
        loadRooms();
        System.out.println("Loaded room data");
        loadBackgrounds();
        System.out.println("Loaded background data");
        loadObjects();
        System.out.println("Loaded object data");
        loadSprites();
        System.out.println("Loaded sprite data");
        loadFonts();
        System.out.println("Loaded font data");
        
        loadGameInfo();
        System.out.println("Loaded Game info");

        
    }

    private void loadFonts() throws IOException {
        File fntDir = new File(game.GameFolder, "fonts/");
        File[] files = fntDir.listFiles(new FileFilter(".dat"));
        game.fonts = new ArrayList<GameFontRes>(files.length);
        for (File f : files) {
            BufferedReader r = new BufferedReader(new FileReader(f));
            GameFontRes fnt = new GameFontRes(r.readLine());
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
        File sprDir = new File(game.GameFolder, "sprites/");
        File[] files = sprDir.listFiles(new FileFilter(".dat"));
        game.sprites = new ArrayList<GameSprite>(files.length);
        for (File f : files) {
            BufferedReader r = new BufferedReader(new FileReader(f));
            GameSprite s = new GameSprite(r.readLine());
            s.setId(Integer.parseInt(r.readLine()));
            s.width = Integer.parseInt(r.readLine());
            s.height = Integer.parseInt(r.readLine());
            s.transparent = Boolean.parseBoolean(r.readLine());
            String shape = r.readLine();
            if (shape.equalsIgnoreCase("RECTANGLE")) {
                s.shape = MaskShape.RECTANGLE;
            } else if (shape.equalsIgnoreCase("PRECISE")) {
                s.shape = MaskShape.PRECISE;
            } else if (shape.equalsIgnoreCase("DIAMOND")) {
                s.shape = MaskShape.DIAMOND;
            } else {
                s.shape = MaskShape.DISK;
            }
            r.readLine(); // alpha - GM8; Ignored
            r.readLine(); // mask - GM8; Ignored
            s.smooth = Boolean.parseBoolean(r.readLine());
            s.preload = Boolean.parseBoolean(r.readLine());
            s.x = Integer.parseInt(r.readLine());
            s.y = Integer.parseInt(r.readLine());
            String mode = r.readLine();
            if (mode.equalsIgnoreCase("AUTO")) {
                s.mode = BBMode.AUTO;
            } else if (mode.equalsIgnoreCase("FULL")) {
                s.mode = BBMode.FULL;
            } else {
                s.mode = BBMode.MANUAL;
            }
            s.left = Integer.parseInt(r.readLine());
            s.right = Integer.parseInt(r.readLine());
            s.top = Integer.parseInt(r.readLine());
            s.bottom = Integer.parseInt(r.readLine());
            String[] subimgs = r.readLine().split(",");
            for (String sub : subimgs) {
                File sf = new File(sprDir, sub);
                s.subImages.add(s.new SubImage(sf));
            }
            r.close();
            game.sprites.add(s);
        }
    }

    private void loadObjects() throws IOException {
        File objDir = new File(game.GameFolder, "objects/");
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
            game.eventManager.addObject(o);
            r.close();
            game.objects.add(o);
        }
    }

    private void loadRooms() throws IOException {
        // First thing is to load rooms
        ArrayList<Integer> rooms = new ArrayList<Integer>();
        ArrayList<String> roomNames = new ArrayList<String>();
        File roomDir = new File(game.GameFolder, "rooms/");
        File roomData = new File(roomDir, "rooms.lst");

        BufferedReader r = new BufferedReader(new FileReader(roomData));
        String line;
        while ((line = r.readLine()) != null) {
            String[] s = line.split(",");
            // room_instruce,6
            roomNames.add(s[0]);
            rooms.add(Integer.parseInt(s[1]));
        }
        r.close();
        int[] ro = new int[rooms.size()];
        for (int i = 0; i < rooms.size(); i++)
            ro[i] = rooms.get(0);
        game.roomOrder = ro;

        game.rooms = new ArrayList<GameRoom>(game.roomOrder.length);
        for (int i = 0; i < game.roomOrder.length; i++) {
            boolean hasCCode;
            File rf = new File(roomDir, roomNames.get(i) + ".dat");
            r = new BufferedReader(new FileReader(rf));
            GameRoom room = new GameRoom(r.readLine());
            room.setId(Integer.parseInt(r.readLine()));
            room.caption = r.readLine();
            room.width = Integer.parseInt(r.readLine());
            room.height = Integer.parseInt(r.readLine());
            room.speed = Integer.parseInt(r.readLine());
            room.persistent = Boolean.parseBoolean(r.readLine());
            room.background_color = new Color(Integer.parseInt(r.readLine()));
            room.draw_background_color = Boolean.parseBoolean(r.readLine());
            hasCCode = Boolean.parseBoolean(r.readLine());
            int bgs = Integer.parseInt(r.readLine());
            room.backgrounds = new GameRoom.Background[bgs];
            for (int j = 0; j < bgs; j++) {
                String bg = r.readLine();
                String[] data = bg.split(",");
                GameRoom.Background b = new GameRoom.Background();
                b.visible = Boolean.parseBoolean(data[0]);
                b.foreground = Boolean.parseBoolean(data[1]);
                b.backgroundId = Integer.parseInt(data[2]);
                b.x = Integer.parseInt(data[3]);
                b.y = Integer.parseInt(data[4]);
                b.tileHoriz = Boolean.parseBoolean(data[5]);
                b.tileVert = Boolean.parseBoolean(data[6]);
                b.hSpeed = Integer.parseInt(data[7]);
                b.vSpeed = Integer.parseInt(data[8]);
                b.stretch = Boolean.parseBoolean(data[9]);
                room.backgrounds[j] = b;
            }
            room.enable_views = Boolean.parseBoolean(r.readLine());
            int views = Integer.parseInt(r.readLine());
            room.views = new GameRoom.View[views];
            for (int j = 0; j < views; j++) {
                String view = r.readLine();
                String[] data = view.split(",");
                GameRoom.View v = new GameRoom.View();
                v.visible = Boolean.parseBoolean(data[0]);
                v.viewX = Integer.parseInt(data[1]);
                v.viewY = Integer.parseInt(data[2]);
                v.viewW = Integer.parseInt(data[3]);
                v.viewH = Integer.parseInt(data[4]);
                v.portX = Integer.parseInt(data[5]);
                v.portY = Integer.parseInt(data[6]);
                v.portW = Integer.parseInt(data[7]);
                v.portH = Integer.parseInt(data[8]);
                v.borderH = Integer.parseInt(data[9]);
                v.borderV = Integer.parseInt(data[10]);
                v.speedH = Integer.parseInt(data[11]);
                v.speedV = Integer.parseInt(data[12]);
                v.objectId = Integer.parseInt(data[13]);

                room.views[j] = v;
            }
            int instances = Integer.parseInt(r.readLine());
            room.staticInstances = new ArrayList<GameRoom.StaticInstance>(instances);
            for (int j = 0; j < instances; j++) {
                String ins = r.readLine();
                String[] data = ins.split(",");
                GameRoom.StaticInstance in = new GameRoom.StaticInstance();
                in.x = Integer.parseInt(data[0]);
                in.y = Integer.parseInt(data[1]);
                in.objectId = Integer.parseInt(data[2]);
                in.id = Integer.parseInt(data[3]);
                String ccode_ref = data[4];
                if (ccode_ref.startsWith("@")) {
                    String file = "c_" + ccode_ref.substring(1) + ".gml";
                    in.creationCode = Code.load(roomDir, file);
                } else {
                    in.creationCode = null;
                }
                room.staticInstances.add(in);
            }
            int tiles = Integer.parseInt(r.readLine());
            room.tiles = new GameRoom.Tile[tiles];
            for (int j = 0; j < tiles; j++) {
                String tile = r.readLine();
                String[] data = tile.split(",");
                Tile t = new Tile();
                t.roomX = Integer.parseInt(data[0]);
                t.roomY = Integer.parseInt(data[1]);
                t.bgX = Integer.parseInt(data[2]);
                t.bgY = Integer.parseInt(data[3]);
                t.width = Integer.parseInt(data[4]);
                t.height = Integer.parseInt(data[5]);
                t.depth = Integer.parseInt(data[6]);
                t.backgroundId = Integer.parseInt(data[7]);
                t.id = Integer.parseInt(data[8]);
                room.tiles[j] = t;
            }
            if (r.readLine() != null) {
                System.err.println("Error! Expected end of room file but there is still more data " + rf);
            }
            r.close();
            // load creation code
            if (hasCCode) {
                room.creation_code = Code.load(roomDir, room.getName() + "_ccode.gml");
            } else {
                room.creation_code = null;
            }
            game.rooms.add(room);
        }
    }

    private void loadBackgrounds() throws IOException {
        File bgDir = new File(game.GameFolder, "backgrounds/");
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
        File settingsFile = new File(game.GameFolder, "Game Information.dat");
        GameInformation g = new GameInformation();
        BufferedReader r = new BufferedReader(new FileReader(settingsFile));
        g.backgroundColor = new Color(Integer.parseInt(r.readLine()));
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

        File infoFile = new File(game.GameFolder, "Game Information.rtf");
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
        BufferedReader r = new BufferedReader(new FileReader(f));
        int parentId = Integer.parseInt(r.readLine());
        int id = Integer.parseInt(r.readLine());
        RLibAction a = RLibManager.getLibAction(parentId, id);
        // Done of Lib stuff
        // Load Action
        Action act = new Action(a);
        int args = Integer.parseInt(r.readLine());
        for (int i = 0; i < args; i++) {
            int kind = Integer.parseInt(r.readLine());
            Argument arg = new Argument(kind);
            if (r.readLine().equals("ref")) {
                arg.ref = true;
            }
            if (a.actionKind == Action.ACT_CODE) {
                arg.code = Code.load(actionFolder, "c_" + r.readLine().substring(1) + ".gml");
            } else
                arg.val = r.readLine();
            act.arguments.add(arg);
        }
        act.appliesTo = Integer.parseInt(r.readLine());
        act.relative = Boolean.parseBoolean(r.readLine());
        act.not = Boolean.parseBoolean(r.readLine());
        r.close();
        return act;
    }

    /**
     * Indents actions by blocks, if's, else's, and repeat's
     * 
     * @param e
     *            the event to indent
     */
    private void indentEvent(Event e) {
        for (int i = 0; i < e.actions.size(); i++) {
            Action a = e.actions.get(i);
            if (a.lib.question) {
                questionIndent(e, i, a);
                i = a.ifAction.actionEnd;
            } else if (a.lib.actionKind == Action.ACT_REPEAT) {
                actionIndent(e, i);
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

    private BlockAction actionIndent(Event e, int index) {
        BlockAction qa = new BlockAction();
        if (index + 1 < e.actions.size()) {
            qa.start = index + 1;
        } else {
            qa.isFake = true;
            return qa;
        }
        Action next = e.actions.get(index + 1);
        if (next.lib.actionKind != Action.ACT_BEGIN) {
            qa.end = index + 1;
            qa.actionEnd = index + 1;
        } else {
            for (int i = index + 2; i < e.actions.size(); i++) {
                Action a = e.actions.get(i);

                if (a.lib.question) {
                    questionIndent(e, i, a);
                    i = a.ifAction.actionEnd;
                } else if (a.lib.actionKind == Action.ACT_REPEAT) {
                    a.repeatAction = actionIndent(e, i);
                    i = a.ifAction.actionEnd;
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
        }
        return qa;
    }

    private static class FileFilter implements FilenameFilter {
        String ext;

        public FileFilter(String ext) {
            this.ext = ext;
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(ext);
        }
    }
}
