package org.gcreator.runero.inst;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import org.gcreator.runero.RuneroCollision;
import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.CollisionEvent;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.EventExecutor;
import org.gcreator.runero.event.EventQueue;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameObject;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.StaticInstance;
import org.gcreator.runero.res.GameSprite;

public class RoomInstance {

    public GameRoom room;
    public ArrayList<ObjectGroup> instanceGroups;
    public Graphics2D graphics;
    public RuneroGame game;

    private int instance_count;
    private int instance_nextid = 100000;

    public RoomInstance(RuneroGame game, GameRoom room) {
        this.game = game;
        this.room = room;
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
            // GMLScript.executeCode(room.creation_code);
        }

        System.out.println("New room " + room.getName() + "(" + room.width + "," + room.height + ")");
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
                // GMLScript.executeCode(i, si.creationCode);
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
        return new Rectangle(0, 0, room.width, room.height);
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
                if (game.input.isKeyDown(type)) {
                    EventExecutor.executeEvent(e, this);
                    System.out.println("Keyboard " + KeyEvent.getKeyText(type));
                }
            }

        // key press
        if (game.eventManager.hasKeyPressEvents)
            for (Event e : game.eventManager.keyPressEvents) {
                int type = Event.getGmKeyName(e.type);
                if (game.input.isKeyPressed(type)) { //TODO: This behaves in the same way as keyDown...
                    EventExecutor.executeEvent(e, this);
                    System.out.println("Key press " + KeyEvent.getKeyText(type));
                }
            }
        // key release
        if (game.eventManager.hasKeyReleaseEvents)
            for (Event e : game.eventManager.keyReleaseEvents) {
                int type = Event.getGmKeyName(e.type);
                if (game.input.isKeyReleased(type)) { //TODO: This behaves in the same way as keyDown...
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
                            for (Instance i : g.instances) {
                                // TODO: PROPER bounding box checking
                                int width = 0, height = 0;
                                GameSprite s = i.getSprite();
                                if (s != null) {
                                    width = s.width / 2;
                                    height = s.height / 2;
                                }
                                if (i.x + width < 0 || i.x - width > room.width || i.y + height < 0
                                        || i.y - height > room.height) {
                                    i.performEvent(e);
                                }
                            }

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

        game.input.clear();
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

    public void render(Graphics2D g) {
        if (room.draw_background_color) {
            g.setColor(room.background_color);
            g.fillRect(0, 0, room.width, room.height);
        }
        g.setColor(Color.WHITE);
        for (GameRoom.Background gb : room.backgrounds) {
            if (gb.visible && !gb.foreground) {
                // Thanks LateralGM XD
                paintBackground(g, gb);
            }
        }

        // Draw instances
        for (ObjectGroup og : instanceGroups)
            for (Instance i : og.instances) {
                if (i.obj.hasEvent(MainEvent.EV_DRAW)) {
                    i.performEvent(i.obj.getMainEvent(MainEvent.EV_DRAW).events.get(0));
                } else {
                    i.draw();
                }
            }

        // Draw Foregrounds
        for (GameRoom.Background gb : room.backgrounds) {
            if (gb.visible && gb.foreground) {
                // Thanks LateralGM XD
                paintBackground(g, gb);
            }
        }

        if (game.eventManager.otherAnimationEnd != null)
            for (Event e : game.eventManager.otherAnimationEnd)
                for (ObjectGroup gr : instanceGroups)
                    if (gr.obj.getId() == e.object.getId())
                        for (Instance i : gr.instances)
                            if (i.image_number > 0 && i.image_index >= i.image_number - 1)
                                i.performEvent(e);

    }

    private void paintBackground(Graphics g, GameRoom.Background b) {
        Rectangle c = g.getClipBounds();
        if (c == null)
            c = new Rectangle(0, 0, room.width, room.height);
        GameBackground bg = RuneroGame.game.getBackground(b.backgroundId);
        if (bg == null)
            return;
        BufferedImage bi = bg.getBackground().getImage();
        if (bi == null)
            return;
        int w = b.stretch ? room.width : bi.getWidth();
        int h = b.stretch ? room.height : bi.getHeight();
        int x = b.x;
        int y = b.y;
        if (b.tileHoriz || b.tileVert) {
            int ncol = 1;
            int nrow = 1;
            if (b.tileHoriz) {
                x = 1 + c.x + ((x + w - 1 - c.x) % w) - w;
                ncol = 1 + (c.x + c.width - x - 1) / w;
            }
            if (b.tileVert) {
                y = 1 + c.y + ((y + h - 1 - c.y) % h) - h;
                nrow = 1 + (c.y + c.height - y - 1) / h;
            }
            for (int row = 0; row < nrow; row++)
                for (int col = 0; col < ncol; col++)
                    g.drawImage(bi, (x + w * col), (y + h * row), w, h, null);
        } else
            g.drawImage(bi, x, y, w, h, null);
    }
}
