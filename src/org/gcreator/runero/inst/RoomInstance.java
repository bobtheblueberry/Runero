package org.gcreator.runero.inst;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.Event;
import org.gcreator.runero.event.EventExecutor;
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
        loadInstances();
        if (room.creation_code != null) {
            // GMLScript.executeCode(room.creation_code);
        }
        // Create Events
        if (game.eventManager.hasCreateEvents)
            EventExecutor.executeEvent(game.eventManager.create, this);

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
    private ObjectGroup getObjectGroup2(int id) {
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
        Collections.sort(instanceGroups);
    }

    public void addInstace(GameObject g, double x, double y) {
        addInstace(g, x, y, 0, 0);
    }

    /**
     * removes the instance from the room
     * 
     * @return whether or not successful
     */
    public boolean destoryInstance(Instance instance) {
        ObjectGroup g = getObjectGroup2(instance.obj.getId());
        if (g == null)
            return false;
        return g.instances.remove(instance);
    }

    public void changeInstance(Instance instance, GameObject newobj, boolean performEvents) {
        if (performEvents) {
            if (instance.obj.hasEvent(MainEvent.EV_DESTROY)) {
                instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_DESTROY).events.get(0));
            }
        }

        // remove from the instance list
        getObjectGroup(instance.obj.getId()).instances.remove(instance);

        instance.obj = newobj;
        // add it to somewhere else
        getObjectGroup(newobj.getId()).add(instance);

        if (performEvents) {
            if (instance.obj.hasEvent(MainEvent.EV_CREATE)) {
                instance.performEvent(instance.obj.getMainEvent(MainEvent.EV_CREATE).events.get(0));
            }
        }
    }

    public void addInstace(GameObject g, double x, double y, double dir, double speed) {
        Instance i = new Instance(x, y, instance_nextid++, g);
        if (speed != 0 || dir != 0) {
            i.motion_set(dir, speed);
        }
        // call create event
        if (g.hasEvent(MainEvent.EV_CREATE)) {
            i.performEvent(g.getMainEvent(MainEvent.EV_COLLISION).events.get(0));
        }
        getObjectGroup(g.getId()).instances.add(i);
    }

    public void step() {
        // begin step, normal step
        if (game.eventManager.hasStepBeginEvents)
            EventExecutor.executeEvent(game.eventManager.stepBegin, this);
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
                for (Event e : game.eventManager.otherOutsideRoom) {
                    for (ObjectGroup g : instanceGroups)
                        if (g.obj.getId() == g.obj.getId()) {
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
                        }
                }

            // TODO: Intersect boundary
            // TODO: collision
            // TODO: No more lives
            // TODO: No more health
        }
        // end step
        if (game.eventManager.hasStepEndEvents)
            EventExecutor.executeEvent(game.eventManager.stepEnd, this);
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
