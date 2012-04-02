package org.gcreator.runero.inst;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.collision.RuneroCollision;
import org.gcreator.runero.event.CollisionEvent;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.EventExecutor;
import org.gcreator.runero.event.EventQueue;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.gfx.GraphicsLibrary;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.StaticInstance;
import org.gcreator.runero.res.GameSprite;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

public class RoomInstance {

    public GameRoom room;
    public ArrayList<ObjectGroup> instanceGroups;
    public RuneroGame game;

    public int width;
    public int height;

    private int instance_count;
    private static int instance_nextid = 100000;

    public RoomInstance(RuneroGame game, GameRoom room) {
        this.game = game;
        this.room = room;
        this.width = room.getWidth();
        this.height = room.getHeight();
    }

    public void init(boolean gameStart) {
        loadInstances();
        // TODO: Object creation code
        // Create Events
        if (game.eventManager.hasCreateEvents)
            EventExecutor.executeEvent(game.eventManager.create, this);

        if (gameStart && game.eventManager.otherGameStart != null)
            EventExecutor.executeEvent(game.eventManager.otherGameStart, this);

        if (room.creation_code != null) {
            //TODO: GMLScript.executeCode(room.creation_code);
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
        for (ObjectGroup g : instanceGroups)
            if (g.obj.getId() == id)
                return g;
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
                // TODO: GMLScript.executeCode(i, si.creationCode);
            }
            getObjectGroup(i.obj.getId()).instances.add(i);
            instance_count++;
            instance_nextid = Math.max(instance_nextid + 1, i.id + 1);
        }
        sortInstances();
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
        instance.isDead = true;
        // destroy event
        if (instance.obj.hasEvent(MainEvent.EV_DESTROY)) {
            instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_DESTROY).events.get(0));
        }
        EventQueue.addDestroyEvent(instance);
    }

    public void changeInstance(Instance instance, GameObject newobj, boolean performEvents) {

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

    public void step() {
        EventQueue.processCreate(this);
        EventQueue.processChange(this);
        EventQueue.processDestroy(this);
        // begin step
        if (game.eventManager.hasStepBeginEvents)
            EventExecutor.executeEvent(game.eventManager.stepBegin, this);

        // TODO: alarm events

        // keyboard
        if (game.eventManager.hasKeyboardEvents)
            for (Event e : game.eventManager.keyboardEvents) {
                int type = Event.getGmKeyName(e.type);
                // if (Keyboard.isKeyDown(type)) {
                // EventExecutor.executeEvent(e, this);
                // System.out.println("Keyboard " + KeyEvent.getKeyText(type));
                // }
            }

        // key press
        if (game.eventManager.hasKeyPressEvents)
            for (Event e : game.eventManager.keyPressEvents) {
                int type = Event.getGmKeyName(e.type);
                if (Keyboard.isKeyDown(type)) { // TODO: This behaves in the same way as keyDown...
                    EventExecutor.executeEvent(e, this);
                    System.out.println("Key press " + KeyEvent.getKeyText(type));
                }
            }
        // key release
        if (game.eventManager.hasKeyReleaseEvents)
            for (Event e : game.eventManager.keyReleaseEvents) {
                int type = Event.getGmKeyName(e.type);
                // TODO: THIs
                if (false) { // TODO: This behaves in the same way as keyDown...
                    EventExecutor.executeEvent(e, this);
                    System.out.println("Key press " + KeyEvent.getKeyText(type));
                }
            }

        // millions of mouse and joystick events

        // normal step
        if (game.eventManager.hasStepNormalEvents)
            EventExecutor.executeEvent(game.eventManager.stepNormal, this);

        // Move objects
        for (ObjectGroup g : instanceGroups)
            for (Instance i : g.instances)
                i.move();

        if (game.eventManager.hasOtherEvents) {
            // TODO: path end
            // outside room
            if (game.eventManager.otherOutsideRoom != null)
                for (Event e : game.eventManager.otherOutsideRoom)
                    for (ObjectGroup g : instanceGroups)
                        if (g.obj.getId() == e.object.getId())
                            for (Instance i : g.instances)
                                if (isOutside(i))
                                    i.performEvent(e);

            // TODO: Intersect boundary
        }
        if (game.eventManager.hasCollisionEvents)
            for (CollisionEvent ce : game.eventManager.collision) {
                // look for instances
                ObjectGroup g = getObjectGroup2(ce.event.object.getId());
                ObjectGroup g2 = getObjectGroup2(ce.otherId);
                if (g == null || g2 == null)
                    continue;
                for (Instance i : g.instances)
                    for (Instance i2 : g2.instances)
                        if (i != i2 && RuneroCollision.checkCollision(i, i2, false)) {
                            ce.collide(i, i2);
                            System.out.println("collision with " + i + " and " + i2);
                        }

            }

        if (game.eventManager.hasOtherEvents) {
            // No more lives
            if (game.eventManager.otherNoMoreLives != null && game.lives <= 0) {
                EventExecutor.executeEvent(game.eventManager.otherNoMoreLives, this);
            }
            // No more health
            if (game.eventManager.otherNoMoreLives != null && game.health <= 0) {
                EventExecutor.executeEvent(game.eventManager.otherNoMoreHealth, this);
            }
        }
        // end step
        if (game.eventManager.hasStepEndEvents)
            EventExecutor.executeEvent(game.eventManager.stepEnd, this);

        // draw
        // have to wait for someone else to call render...
    }

    public Instance getInstance(int id) {
        for (ObjectGroup g : instanceGroups)
            for (Instance i : g.instances)
                if (i.id == id)
                    return i;

        return null;
    }

    public void render(GraphicsLibrary g) {
        if (room.draw_background_color) {
            g.setColor(room.background_color);
            g.fillRect(0, 0, width, height);
        }
        // draw backgrounds
        drawBackgrounds(g, false);
        // Draw instances
        for (ObjectGroup og : instanceGroups)
            for (Instance i : og.instances) {
                if (i.obj.hasEvent(MainEvent.EV_DRAW)) {
                    i.performEvent(i.obj.getMainEvent(MainEvent.EV_DRAW).events.get(0));
                } else {
                    System.out.println("Drawing instance of " + i.obj.getName());
                    i.draw(g);
                }
            }

        // Draw Foregrounds
        drawBackgrounds(g, true);

        // Animation end
        if (game.eventManager.otherAnimationEnd != null)
            for (Event e : game.eventManager.otherAnimationEnd)
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
