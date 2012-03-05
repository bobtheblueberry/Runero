package org.gcreator.runero.inst;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.event.MainEvent;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameRoom;
import org.gcreator.runero.res.GameRoom.StaticInstance;
import org.gcreator.runero.script.GMLScript;

public class RoomInstance {

    public GameRoom room;
    public ArrayList<Instance> instances;
    public Graphics2D graphics;

    private void loadInstances() {
        instances = new ArrayList<Instance>(room.staticInstances.size());
        for (StaticInstance si : room.staticInstances) {
            Instance i = new Instance(si);
            // do instance creation code
            if (si.creationCode != null) {
                GMLScript.executeCode(i, si.creationCode);
            }
            instances.add(i);
        }
    }

    public RoomInstance(GameRoom room) {
        this.room = room;
        loadInstances();
        if (room.creation_code != null) {
            GMLScript.executeCode(room.creation_code);
        }
    }

    public Instance getInstance(int id) {
        for (Instance i : instances) {
            if (i.id == id)
                return i;
        }
        return null;
    }

    public ArrayList<Instance> getInstances(int objectType) {
        ArrayList<Instance> list = new ArrayList<Instance>();
        for (Instance i : instances) {
            if (i.obj.getId() == objectType)
                list.add(i);
        }
        return list;
    }

    public void render(Graphics2D g) {
        if (room.draw_background_color) {
            g.setColor(room.background_color);
            g.fillRect(0, 0, room.width, room.height);
        }
        g.setColor(Color.WHITE);
        for (GameRoom.Background b : room.backgrounds) {
            if (b.visible) {
                GameBackground back = RuneroGame.game.getBackground(b.backgroundId);
                if (back != null) {
                    g.drawImage(back.getBackground().getImage(), b.x, b.y, null);
                }
            }
        }
        
        // Draw instances
        for (Instance i : instances) {
            i.performEvent(MainEvent.EV_DRAW);
        }
    }
}
