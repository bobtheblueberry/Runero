package org.gcreator.runero.inst;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.collision.RuneroCollision;
import org.gcreator.runero.event.CollisionEvent;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.EventExecutor;
import org.gcreator.runero.event.EventQueue;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.gfx.Texture;
import org.gcreator.runero.gml.lib.KeyboardLibrary;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.Background;
import org.gcreator.runero.res.GameRoom.StaticInstance;
import org.gcreator.runero.res.GameRoom.Tile;
import org.gcreator.runero.res.GameRoom.View;
import org.gcreator.runero.res.GameSprite;
import org.lwjgl.input.Keyboard;

public class RoomInstance {

    public GameRoom room;
    public ArrayList<ObjectGroup> instanceGroups;
    public RuneroGame game;

    public int width;
    public int height;
    public Color background_color;
    public boolean draw_background_color;
    public String caption;

    private int instance_count;
    private static int instance_nextid = 100000;
    public Background[] backgrounds;
    public View[] views;
    public Tile[] tiles;

    public RoomInstance(RuneroGame game, GameRoom room)
        {
            this.game = game;
            this.room = room;
            this.width = room.getWidth();
            this.height = room.getHeight();
            this.background_color = room.background_color;
            this.draw_background_color = room.draw_background_color;
            this.caption = room.caption;
            backgrounds = new Background[room.backgrounds.length];
            for (int i = 0; i < backgrounds.length; i++)
                backgrounds[i] = room.backgrounds[i].copy();
        }

    public void init(boolean gameStart) {
        loadInstances();

        // Create Events
        if (game.em.hasCreateEvents)
            EventExecutor.executeEvent(game.em.create, this);

        if (gameStart && game.em.otherGameStart != null)
            EventExecutor.executeEvent(game.em.otherGameStart, this);

        if (room.creation_code != null) {
            room.creation_code.getCode().execute(null, null);
        }

        System.out.println("New room " + room.getName() + "(" + width + "," + height + ")");
        System.out.println("Instances: " + instance_count);
    }

    public ObjectGroup getObjectGroup(int id) {
        for (ObjectGroup g : instanceGroups)
            if (g.obj.getId() == id)
                return g;
        GameObject o = RuneroGame.game.getObject(id);
        if (o == null)
            return null;
        ObjectGroup g = new ObjectGroup(o);
        instanceGroups.add(g);
        return g;
    }

    /**
     * returns null if there isn't one
     * 
     * @param id
     * @return
     */
    public ObjectGroup getObjectGroup2(int id) {
        for (ObjectGroup g : instanceGroups) {
            if (g.obj.getId() == id)
                return g;
        }
        return null;
    }

    public boolean hasObjectGroup(int id) {
        for (ObjectGroup g : instanceGroups)
            if (g.obj.getId() == id)
                return true;
        return false;
    }

    private void loadInstances() {
        instanceGroups = new ArrayList<ObjectGroup>();
        for (StaticInstance si : room.staticInstances) {
            Instance i = new Instance(si);
            // do instance creation code
            if (si.creationCode != null) {
                si.creationCode.getCode().execute(i, null);
            }
            getObjectGroup(i.obj.getId()).instances.add(i);
            instance_count++;
            instance_nextid = Math.max(instance_nextid + 1, i.id + 1);
        }
        sortInstances();
    }

    public int getInstanceCount() {
        return instance_count;
    }

    private void sortInstances() {
        Collections.sort(instanceGroups);
        for (ObjectGroup g : instanceGroups)
            Collections.sort(g.instances);
    }

    public void addInstace(GameObject g, double x, double y) {
        addInstace(g, x, y, 0, 0);
    }

    public Rectangle getRectangle() {
        // TODO: Account for room views if they are used
        return new Rectangle(0, 0, width, height);
    }

    /**
     * Removes the instance from the room LATER ON
     * and calls the instances destroy event,
     * if is has one.
     * 
     */
    public void destoryInstance(Instance instance) {
        System.out.println("Destroy " + instance);
        instance.isDead = true;
        // destroy event
        if (instance.obj.hasEvent(MainEvent.EV_DESTROY)) {
            instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_DESTROY).events.get(0));
        }
        EventQueue.addDestroyEvent(instance);
    }

    public void changeInstance(Instance instance, GameObject newobj, boolean performEvents) {
        System.out.println("Change " + instance + " into " + newobj.getName());
        EventQueue.addChangeEvent(instance, instance.obj.getId());
        try {
            if (performEvents) {
                if (instance.obj.hasEvent(MainEvent.EV_DESTROY)) {
                    instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_DESTROY).events.get(0));
                }
            }
        } catch (Exception exc) {
            System.err.println("Exception performing destroy event in instance change");
            exc.printStackTrace();
        }
        instance.obj = newobj;

        if (performEvents) {
            if (instance.obj.hasEvent(MainEvent.EV_CREATE)) {
                instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_CREATE).events.get(0));
            }
        }
    }

    public void addInstace(GameObject g, double x, double y, double dir, double speed) {
        Instance i = new Instance(x, y, instance_nextid++, g);
        EventQueue.addCreateEvent(i);
        if (speed != 0 || dir != 0) {
            i.motion_set(dir, speed);
        }
        // call create event
        if (g.hasEvent(MainEvent.EV_CREATE)) {
            i.performEvent(g.getMainEvent(MainEvent.EV_CREATE).events.get(0));
        }
    }

    public void addInstance(Instance i) {
        EventQueue.addCreateEvent(i);
    }

    public void step() {
        EventQueue.processCreate(this);
        EventQueue.processChange(this);
        EventQueue.processDestroy(this);
        // begin step
        if (game.em.hasStepBeginEvents)
            EventExecutor.executeEvent(game.em.stepBegin, this);

        if (game.em.hasAlarmEvents)
            for (int a = 0; a < game.em.alarms.length; a++) {
                LinkedList<Event> l = game.em.alarms[a];
                if (l == null)
                    continue;
                for (Event e : l)
                    for (ObjectGroup g : instanceGroups)
                        if (g.obj.getId() == e.object.getId())

                            for (Instance i : g.instances) {
                                if (i.alarm[a] <= 0)
                                    continue;
                                i.alarm[a]--;
                                if (i.alarm[a] == 0)
                                    i.performEvent(e);
                            }
            }

        handleKeyboard();
        // millions of mouse and joystick events

        // normal step
        if (game.em.hasStepNormalEvents)
            EventExecutor.executeEvent(game.em.stepNormal, this);

        // Move objects
        for (ObjectGroup g : instanceGroups)
            for (Instance i : g.instances)
                i.move();

        if (game.em.hasOtherEvents) {
            // TODO: path end
            // outside room
            if (game.em.otherOutsideRoom != null)
                for (Event e : game.em.otherOutsideRoom)
                    for (ObjectGroup g : instanceGroups)
                        if (g.obj.getId() == e.object.getId())
                            for (Instance i : g.instances)
                                if (isOutside(i))
                                    i.performEvent(e);

            // TODO: Intersect boundary
        }

        if (game.em.hasCollisionEvents)
            for (CollisionEvent ce : game.em.collision) {
                // look for instances
                ObjectGroup g = getObjectGroup2(ce.event.object.getId());
                ObjectGroup g2 = getObjectGroup2(ce.otherId);
                if (g == null || g2 == null)
                    continue;
                for (Instance i : g.instances)
                    for (Instance i2 : g2.instances)
                        if (i != i2 && RuneroCollision.checkCollision(i, i2, false))
                            ce.collide(i, i2);
            }
        if (game.em.hasOtherEvents) {
            // No more lives

            if (game.em.otherNoMoreLives != null && game.lives <= 0) {
                EventExecutor.executeEvent(game.em.otherNoMoreLives, this);
            }

            // No more health
            if (game.em.otherNoMoreLives != null && game.health <= 0) {
                EventExecutor.executeEvent(game.em.otherNoMoreHealth, this);
            }
        }

        // end step
        if (game.em.hasStepEndEvents)
            EventExecutor.executeEvent(game.em.stepEnd, this);

        // draw
        // have to wait for someone else to call render...
    }

    private void handleKeyboard() {
        // if key is down
        if (game.em.hasKeyboardEvents)
            for (Event e : game.em.keyboardEvents) {
                boolean fired = false;
                for (int i : KeyboardLibrary.getCodes(e.type))
                    if (i > 0 && Keyboard.isKeyDown(i)) {
                        fired = true;
                        break;
                    }
                if (fired)
                    EventExecutor.executeEvent(e, this);
            }
        boolean keyPressEvents = false;
        boolean keyReleaseEvents = false;
        while (Keyboard.next()) {
            boolean state = Keyboard.getEventKeyState();
            Integer key = Keyboard.getEventKey();
            if (state)
                KeyboardLibrary.keyboardDownKeys.add(key);
            else
                KeyboardLibrary.keyboardDownKeys.remove(key);
            if (!game.em.hasKeyPressEvents && !game.em.hasKeyReleaseEvents)
                continue;

            if (!keyPressEvents && state) {
                keyPressEvents = true;
                if (game.em.keyPressAnyEvents != null)
                    for (Event e : game.em.keyPressAnyEvents) {
                        EventExecutor.executeEvent(e, this);
                    }
            } else if (!keyReleaseEvents && !state) {
                keyReleaseEvents = true;
                if (game.em.keyReleaseAnyEvents != null)
                    for (Event e : game.em.keyReleaseAnyEvents)
                        EventExecutor.executeEvent(e, this);
            }
            char c = Keyboard.getEventCharacter();
            if (c > 0) {
                game.keyboard_string += c;
                game.keyboard_lastchar = String.valueOf(c);
            }

            // key press
            if (game.em.hasKeyPressEvents && state)
                for (Event e : game.em.keyPressEvents) {
                    boolean fired = false;
                    for (int i : KeyboardLibrary.getCodes(e.type))
                        if (i == Keyboard.getEventKey() && Keyboard.getEventKeyState()) {
                            fired = true;
                            break;
                        }
                    if (fired)
                        EventExecutor.executeEvent(e, this);
                }

            // key release
            if (game.em.hasKeyReleaseEvents && !state)
                for (Event e : game.em.keyReleaseEvents) {
                    boolean fired = false;
                    for (int i : KeyboardLibrary.getCodes(e.type))
                        if (i == Keyboard.getEventKey() && !Keyboard.getEventKeyState()) {
                            fired = true;
                            break;
                        }
                    if (fired)
                        EventExecutor.executeEvent(e, this);
                }
        }
        if (KeyboardLibrary.keyboardDownKeys.isEmpty() && game.em.keyboardNoEvents != null)
            for (Event e : game.em.keyboardNoEvents)
                EventExecutor.executeEvent(e, this);
        if (!KeyboardLibrary.keyboardDownKeys.isEmpty() && game.em.keyboardAnyEvents != null)
            for (Event e : game.em.keyboardAnyEvents)
                EventExecutor.executeEvent(e, this);

        if (!keyPressEvents && game.em.keyPressNoEvents != null)
            for (Event e : game.em.keyPressNoEvents)
                EventExecutor.executeEvent(e, this);
        if (!keyReleaseEvents && game.em.keyReleaseNoEvents != null)
            for (Event e : game.em.keyReleaseNoEvents)
                EventExecutor.executeEvent(e, this);

    }

    public Instance getInstance(int id) {
        for (ObjectGroup g : instanceGroups)
            for (Instance i : g.instances)
                if (i.id == id)
                    return i;

        return null;
    }

    public void render(GraphicsLibrary g) {
        if (draw_background_color) {
            g.setColor(background_color);
            g.fillRect(0, 0, width, height);
        }
        // draw backgrounds
        drawBackgrounds(g, false);
        // Draw instances
        for (ObjectGroup og : instanceGroups)
            for (Instance i : og.instances)
                if (i.obj.hasEvent(MainEvent.EV_DRAW))
                    i.performEvent(i.obj.getMainEvent(MainEvent.EV_DRAW).events.get(0));
                else
                    i.draw(g);

        // Draw Foregrounds
        drawBackgrounds(g, true);

        // Animation end
        if (game.em.otherAnimationEnd != null)
            for (Event e : game.em.otherAnimationEnd)
                for (ObjectGroup gr : instanceGroups)
                    if (gr.obj.getId() == e.object.getId())
                        for (Instance i : gr.instances)
                            if (i.image_number > 0 && i.image_index >= i.image_number - 1)
                                i.performEvent(e);

    }

    private void drawBackgrounds(GraphicsLibrary g, boolean foreground) {
        for (GameRoom.Background gb : room.backgrounds) {
            if (gb.visible && gb.foreground == foreground) {
                paintBackground(g, gb);
            }
        }
    }

    private void paintBackground(GraphicsLibrary g, GameRoom.Background b) {
        GameBackground bg = RuneroGame.game.getBackground(b.backgroundId);
        if (bg == null)
            return;
        Texture t = bg.getBackground();
        if (t == null)
            return;
        g.drawBackground(bg, b.x, b.y, b.tileHoriz, b.tileVert, b.stretch);
    }

    private boolean isOutside(Instance i) {
        // If the instance has a sprite, make sure the entire
        // sprite is outside the room, just like Game Maker does

        GameSprite s = i.getSprite();
        if (s == null) {
            return (i.x < 0 || i.y < 0 || i.x > width || i.y > height);
        }

        double x1 = i.x - s.x;
        double y1 = i.y - s.y;
        double x2 = i.x + s.width - s.x;
        double y2 = i.y + s.height - s.y;

        if (x1 > width || y1 > height || x2 < 0 || y2 < 0) {
            return true;
        }

        return false;
    }

}
